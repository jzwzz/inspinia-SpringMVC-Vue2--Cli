/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.controller;

import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.cmb.ccd.mr.rtm.guardian.archive.form.ReviewForm;
import com.cmb.ccd.mr.rtm.guardian.archive.service.ArchiveService;
import com.cmb.ccd.mr.rtm.guardian.archive.vo.SendMessageResultVO;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseConstants;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("archive")
@Log4j
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private ArchiveRepository archiveRepository;

    @RequestMapping("load")
    public ResponseResult loadArchive() {
        int fileSize;
        try {
            fileSize = archiveService.loadArchiveFromSystem();
            return new ResponseResult<>(fileSize);
        } catch (SQLException e) {
            return new ResponseResult<>(ResponseConstants.DATABASE_EXCEPTION, e.toString());
        }
    }

    @GetMapping("listByCreatorId")
    @Transactional(readOnly = true)
    public ResponseResult<List<Archive>> findArchiveByCreatorId(String creatorId) {
        List<Archive> archiveList = archiveRepository.findByCreatorIdOrCreatorIdOrderByCreateDateTimeDesc(creatorId,
                Archive.DEFAULT_CREATOR_ID);
        return new ResponseResult<>(archiveList);
    }

    @GetMapping("listByReviewerId")
    @Transactional(readOnly = true)
    public ResponseResult<List<Archive>> findArchiveByReviewerId(String reviewerId) {
        List<Archive> archiveList = archiveRepository.findByReviewerIdOrderByCreateDateTimeDesc(reviewerId);
        return new ResponseResult<>(archiveList);
    }

    @GetMapping("list")
    @Transactional(readOnly = true)
    public ResponseResult<List<Archive>> findAll() {
        List<Archive> archiveList = archiveRepository.findAllByOrderByCreateDateTimeDesc();
        return new ResponseResult<>(archiveList);
    }

    @GetMapping("{archiveId}")
    @Transactional(readOnly = true)
    public ResponseResult<Archive> findArchiveById(@PathVariable long archiveId) {
        Archive archive = archiveRepository.findOne(archiveId);
        return new ResponseResult<>(archive);
    }

    @PreAuthorize("hasRole('ROLE_RMG01')")
    @PostMapping(value = "projectManager/review/{archiveId}")
    public ResponseResult projectManagerReview(@RequestBody ReviewForm reviewForm) {
        try {
            archiveService.projectManagerReview(reviewForm);
        } catch (Exception e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.INTERNAL_ERROR, e.toString());
        }
        return new ResponseResult("SUCCESS");
    }

    @PreAuthorize("hasRole('ROLE_RMG02')")
    @PostMapping(value = "superior/review/{archiveId}")
    public ResponseResult<SendMessageResultVO> superiorReview(@RequestBody ReviewForm reviewForm) {
        SendMessageResultVO sendMessageResultVO;
        try {
            sendMessageResultVO = archiveService.superiorReview(reviewForm);
        } catch (Exception e) {
            log.error(e);
            return new ResponseResult<>(ResponseConstants.INTERNAL_ERROR, e.toString());
        }
        return new ResponseResult<>(sendMessageResultVO);
    }

    @PostMapping(value = "upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile myFile, User user) {
        try {
            archiveService.loadArchiveFromUser(myFile, user);
            return new ResponseResult("SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseResult<>(ResponseConstants.DATABASE_EXCEPTION, e.toString());
        } catch (Exception e) {
            log.error(e);
            return new ResponseResult<>(ResponseConstants.INTERNAL_ERROR, e.toString());
        }
    }

    @GetMapping(value = "sample")
    @ResponseBody
    public ResponseEntity getFile() {
        File file = archiveService.getSampleFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(fileSystemResource);
    }

}
