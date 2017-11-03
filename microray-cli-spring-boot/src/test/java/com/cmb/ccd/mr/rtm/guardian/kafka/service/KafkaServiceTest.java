package com.cmb.ccd.mr.rtm.guardian.kafka.service;

import com.cmb.ccd.mr.rtm.guardian.AnnotationBaseTest;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.cmb.ccd.mr.rtm.guardian.archive.vo.SendMessageResultVO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Ignore
public class KafkaServiceTest extends AnnotationBaseTest {

    @Autowired
    private KafkaService kafkaService;

    @Test
    public void sendRecordWithFailedRecord() throws IOException, InterruptedException, ExecutionException {
        String url = "com/cmb/ccd/mr/rtm/guardian/archive/sample/PUB_F_CRD_TRX_REAL_20171011";
        String filePath = ResourceUtils.getFile("classpath:" + url).getPath();
        List<String> lineList = Files.readAllLines(Paths.get(filePath));
        List<ArchiveDetail> archiveDetailList = lineList.stream().map(ArchiveDetail::new).collect(Collectors.toList());
        archiveDetailList.get(0).setApprovalTimestamp("20170911");
        Future<SendMessageResultVO> future = kafkaService.sendRecord(archiveDetailList);
        SendMessageResultVO sendMessageResultVO = future.get();
        Assert.assertEquals(19, sendMessageResultVO.getSuccessRecords());
        Assert.assertEquals(1, sendMessageResultVO.getFailedRecords());
    }

    @Test
    public void sendRecordWithoutFailedRecord() throws IOException, InterruptedException, ExecutionException {
        String url = "com/cmb/ccd/mr/rtm/guardian/archive/sample/PUB_F_CRD_TRX_REAL_20171011";
        String filePath = ResourceUtils.getFile("classpath:" + url).getPath();
        List<String> lineList = Files.readAllLines(Paths.get(filePath));
        List<ArchiveDetail> archiveDetailList = lineList.stream().map(ArchiveDetail::new).collect(Collectors.toList());
        archiveDetailList.get(0).setAccountType("001");
        archiveDetailList.get(0).setMessageType("0220");
        Future<SendMessageResultVO> future = kafkaService.sendRecord(archiveDetailList);
        SendMessageResultVO sendMessageResultVO = future.get();
        Assert.assertEquals(20, sendMessageResultVO.getSuccessRecords());
        Assert.assertEquals(0, sendMessageResultVO.getFailedRecords());
    }

}