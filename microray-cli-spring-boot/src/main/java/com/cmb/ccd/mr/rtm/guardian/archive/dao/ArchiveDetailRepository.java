package com.cmb.ccd.mr.rtm.guardian.archive.dao;

import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveDetailRepository extends JpaRepository<ArchiveDetail, Long> {

    Page<ArchiveDetail> findByArchiveId(long archiveId, Pageable pageable);

    List<ArchiveDetail> findBySysIdAndArchiveIdInOrderByApprovalTimestamp(String sysId, List<Long> archiveIds);
}
