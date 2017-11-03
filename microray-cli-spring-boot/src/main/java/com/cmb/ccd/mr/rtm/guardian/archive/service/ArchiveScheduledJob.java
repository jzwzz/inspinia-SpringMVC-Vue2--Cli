/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Log4j
@Component
public class ArchiveScheduledJob {
    @Autowired
    private ArchiveService archiveService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void scheduledLoadArchive() {
        log.info("start scheduled job.");
        int size = 0;
        try {
            size = archiveService.loadArchiveFromSystem();
        } catch (SQLException e) {
            log.error("load file error.");
        }
        log.info("load " + size + " files");
    }
}
