package com.cmb.ccd.mr.rtm.guardian.archive.dao;

import java.io.File;
import java.sql.SQLException;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
public interface FileRepository {

    long fileInsert(String path, Long archiveId) throws SQLException;
}
