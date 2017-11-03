/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.service;

import com.cmb.ccd.mr.rtm.guardian.archive.form.ReviewForm;
import com.cmb.ccd.mr.rtm.guardian.archive.vo.SendMessageResultVO;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public interface ArchiveService {

    int loadArchiveFromSystem() throws SQLException;

    int loadArchive(String path, User system) throws SQLException;

    void projectManagerReview(ReviewForm reviewForm);

    void loadArchiveFromUser(MultipartFile myFile, User user) throws IOException, SQLException;

    File getSampleFile();

    SendMessageResultVO superiorReview(ReviewForm reviewForm);
}
