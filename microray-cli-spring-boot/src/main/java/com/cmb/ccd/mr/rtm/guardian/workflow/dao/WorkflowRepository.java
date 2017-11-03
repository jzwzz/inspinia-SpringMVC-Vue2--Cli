package com.cmb.ccd.mr.rtm.guardian.workflow.dao;

import com.cmb.ccd.mr.rtm.guardian.workflow.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    List<Workflow> findByArchiveId(long archiveId);
}
