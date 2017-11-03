/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.dao;

import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    List<Archive> findByCreatorIdOrCreatorIdOrderByCreateDateTimeDesc(String creatorId, String system);

    List<Archive> findByReviewerIdOrderByCreateDateTimeDesc(String reviewerId);

    List<Archive> findByStatusIn(List<ArchiveStatus> statuses);

    List<Archive> findAllByOrderByCreateDateTimeDesc();
}
