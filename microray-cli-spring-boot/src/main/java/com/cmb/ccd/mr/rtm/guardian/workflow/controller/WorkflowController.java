/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.workflow.controller;

import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.workflow.dao.WorkflowRepository;
import com.cmb.ccd.mr.rtm.guardian.workflow.entity.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("workflow")
public class WorkflowController {

    @Autowired
    private WorkflowRepository workflowRepository;

    @RequestMapping("list")
    @Transactional(readOnly = true)
    public ResponseResult<List<Workflow>> findByArchiveId(long archiveId) {
        List<Workflow> workflowList = workflowRepository.findByArchiveId(archiveId);
        return new ResponseResult<>(workflowList);
    }
}
