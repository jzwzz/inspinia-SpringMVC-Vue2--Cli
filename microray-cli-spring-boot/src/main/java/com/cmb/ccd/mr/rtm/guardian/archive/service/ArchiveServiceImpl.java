/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.service;

import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveDetailRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.dao.FileRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveStatus;
import com.cmb.ccd.mr.rtm.guardian.archive.form.ReviewForm;
import com.cmb.ccd.mr.rtm.guardian.archive.vo.SendMessageResultVO;
import com.cmb.ccd.mr.rtm.guardian.kafka.service.KafkaService;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import com.cmb.ccd.mr.rtm.guardian.workflow.dao.WorkflowRepository;
import com.cmb.ccd.mr.rtm.guardian.workflow.entity.Workflow;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Log4j
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private ArchiveDetailRepository archiveDetailRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private WorkflowRepository workflowRepository;
    @Autowired
    private KafkaService kafkaService;

    @Value("${system.file.path}")
    private String systemPath;
    @Value("${upload.file.path}")
    private String uploadPath;
    @Value("${sample.file.Name}")
    private String sampleFileName;
    @Value("${page.size}")
    private int pageSize;

    @Override
    @Transactional
    public int loadArchiveFromSystem() throws SQLException {
        return loadArchive(systemPath, User.system());
    }

    @Override
    @Transactional
    public void loadArchiveFromUser(MultipartFile myFile, User user) throws IOException, SQLException {
        List<String> archiveNameList = loadArchiveFileInfo(uploadPath);
        if (archiveNameList.contains(myFile.getOriginalFilename()))
            throw new IllegalArgumentException("archive with the same file name has already exists");
        String archiveName = saveFile(myFile);
        persistArchiveAndDetails(uploadPath, user, archiveName);
    }

    @Override
    public File getSampleFile() {
        return new File(uploadPath + "/" + sampleFileName);
    }

    @Override
    public int loadArchive(String path, User user) throws SQLException {
        List<String> archiveNameList = loadArchiveFileInfo(path);
        List<String> existArchiveNameList = archiveRepository.findAll().stream().map(Archive::getFileName).collect(Collectors.toList());
        log.info("find already exist file: " + existArchiveNameList);
        archiveNameList.removeAll(existArchiveNameList);

        for (String archiveName : archiveNameList) {
            persistArchiveAndDetails(path, user, archiveName);
        }
        return archiveNameList.size();
    }

    private void persistArchiveAndDetails(String path, User user, String archiveName) throws SQLException {
        Archive archive = new Archive(archiveName, user);
        archiveRepository.save(archive);
        log.info("save batch file: " + archiveName);

        long startTimestamp = new Date().getTime();
        long size = fileRepository.fileInsert(path + "/" + archiveName, archive.getId());
        long endTimestamp = new Date().getTime();
        log.info("save archive detail: " + size + ", cost time " + (endTimestamp - startTimestamp) + "ms");
        archive.setSize(size);
    }

    private List<String> loadArchiveFileInfo(String path) {
        File file = new File(path);
        log.info("load file path is " + path);
        File[] fileArray = file.listFiles();
        assert fileArray != null;
        return Arrays.stream(fileArray).map(File::getName).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void projectManagerReview(ReviewForm reviewForm) {
        Archive archive = archiveRepository.findOne(reviewForm.getArchiveId());
        archive.setReviewerId(reviewForm.getReviewerId());
        archive.setReviewerName(reviewForm.getReviewerName());
        Workflow.OpType opType;
        if (reviewForm.isAccept()) {
            archive.setStatus(ArchiveStatus.待放行);
            opType = Workflow.OpType.审核;
        } else {
            archive.setStatus(ArchiveStatus.作废);
            opType = Workflow.OpType.作废;
        }
        Workflow workflow = new Workflow(opType, reviewForm.getOpUserId(), reviewForm.getOpUserName(), reviewForm.getRemark());
        workflow.setArchive(archive);
        workflowRepository.save(workflow);
    }

    @Transactional
    @Override
    public SendMessageResultVO superiorReview(ReviewForm reviewForm) {
        SendMessageResultVO sendMessageResultVO = new SendMessageResultVO();
        Archive archive = archiveRepository.findOne(reviewForm.getArchiveId());
        Workflow.OpType opType;
        if (reviewForm.isAccept()) {
            archive.setStatus(ArchiveStatus.放行通过);
            opType = Workflow.OpType.放行;
            archive.setSendDateTime(new Date());
            sendMessageResultVO = sendMessagePageable(archive.getId());
        } else {
            archive.setStatus(ArchiveStatus.作废);
            opType = Workflow.OpType.作废;
        }
        Workflow workflow = new Workflow(opType, reviewForm.getOpUserId(), reviewForm.getOpUserName(), reviewForm.getRemark());
        workflow.setArchive(archive);
        workflowRepository.save(workflow);
        return sendMessageResultVO;
    }

    private SendMessageResultVO sendMessagePageable(long archiveId) {
        long start = System.currentTimeMillis();
        int pageIndex = 0;
        List<Future<SendMessageResultVO>> futureList = new ArrayList<>();
        Page<ArchiveDetail> archiveDetailPage = archiveDetailRepository.findByArchiveId(archiveId, new PageRequest(pageIndex, pageSize));
        log.info(archiveDetailPage.getTotalElements() + " records is sending");
        futureList.add(kafkaService.sendRecord(archiveDetailPage.getContent()));
        while (archiveDetailPage.hasNext()) {
            archiveDetailPage = archiveDetailRepository.findByArchiveId(archiveId, archiveDetailPage.nextPageable());
            futureList.add(kafkaService.sendRecord(archiveDetailPage.getContent()));
        }

        SendMessageResultVO sendMessageResultVO = buildSendMessageResultVO(futureList);
        long end = System.currentTimeMillis();
        log.info("finish sending, cost " + (end - start) + "ms totally");
        return sendMessageResultVO;
    }

    private SendMessageResultVO buildSendMessageResultVO(List<Future<SendMessageResultVO>> futureList) {
        SendMessageResultVO sendMessageResultVO = new SendMessageResultVO();
        try {
            for (Future<SendMessageResultVO> future : futureList) {
                sendMessageResultVO.merge(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return sendMessageResultVO;
    }

    private String saveFile(MultipartFile myFile) throws IOException {
        File newFile = new File(uploadPath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        String fileName = myFile.getOriginalFilename();
        String path = uploadPath + "/" + fileName;

        File nativeFile = new File(path);
        myFile.transferTo(nativeFile);
        return fileName;
    }

}
