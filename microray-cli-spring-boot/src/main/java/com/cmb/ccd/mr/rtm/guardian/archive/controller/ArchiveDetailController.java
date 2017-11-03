package com.cmb.ccd.mr.rtm.guardian.archive.controller;

import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveDetailRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveStatus;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("archive")
public class ArchiveDetailController {

    @Autowired
    private ArchiveDetailRepository archiveDetailRepository;
    @Autowired
    private ArchiveRepository archiveRepository;

    @GetMapping("detail/{archiveId}")
    @Transactional(readOnly = true)
    public ResponseResult<Page<ArchiveDetail>> findByArchiveId(@PathVariable long archiveId, Pageable pageable) {
        Page<ArchiveDetail> page = archiveDetailRepository.findByArchiveId(archiveId, pageable);
        return new ResponseResult<>(page);
    }

    @GetMapping("query")
    @Transactional
    public ResponseResult<List<ArchiveDetail>> findBySysId(@RequestParam("sysId") String sysId) {
        List<Archive> pendingArchive = archiveRepository.findByStatusIn(Lists.newArrayList(ArchiveStatus.待审核, ArchiveStatus.待放行));
        List<ArchiveDetail> archiveDetails = archiveDetailRepository.findBySysIdAndArchiveIdInOrderByApprovalTimestamp(sysId, pendingArchive.stream().map(Archive::getId).collect(Collectors.toList()));
        return new ResponseResult<>(archiveDetails);
    }

}
