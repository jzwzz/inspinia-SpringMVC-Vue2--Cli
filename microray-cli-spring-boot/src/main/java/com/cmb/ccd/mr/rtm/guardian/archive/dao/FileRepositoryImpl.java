/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Repository
@Log4j
public class FileRepositoryImpl implements FileRepository {

    @Autowired
    DataSource dataSource;

    @Override
    public long fileInsert(String filePath, Long archiveId) throws SQLException {
        SQLServerDataTable sourceDataTable = buildSqlServerDataTable();
        int count = 0;
        try {
            StopWatch stopWatch = new StopWatch("save file");
            int bufferSize = 50 * 1024 * 1024;
            int pageInsertCount = 1000000;
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader, bufferSize);
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] element = line.split("\\|~\\|");
                if (element.length < 14) {
                    throw new IllegalArgumentException("the size of content args array is less than 14");
                }
                sourceDataTable.addRow(element[0], element[1], element[2], element[3], element[4], element[5], element[6],
                        element[7], element[8], element[9], element[10], element[11], element[12], element[13], archiveId);
                count++;
                if (count % pageInsertCount == 0) {
                    stopWatch.start("save 100w");
                    batchInsert(sourceDataTable);
                    sourceDataTable.clear();
                    sourceDataTable = buildSqlServerDataTable();
                    stopWatch.stop();
                    log.info("save 100w:" + stopWatch.shortSummary());
                }
            }
            reader.close();
            stopWatch.start("save last");
            batchInsert(sourceDataTable);
            stopWatch.stop();
            log.info("persist finish:" + stopWatch.shortSummary());
            log.info(stopWatch.prettyPrint());
        } catch (IOException e) {
            log.error("read file error", e);
        }
        return count;
    }

    private void batchInsert(SQLServerDataTable sourceDataTable) throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            // Pass the data table as a table-valued parameter using a prepared statement.
            SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(
                    "INSERT INTO archive_detail(card_number,transaction_code,transaction_amount,message_type," +
                            "approval_code,response_code,filler_field,account_type,retailer_id,sys_id,transaction_type," +
                            "approval_timestamp,sequence_number,proxy_approval_flag,archive_id) SELECT * FROM ?;");
            pStmt.setStructured(1, "archive_detail_type", sourceDataTable);
            pStmt.execute();
            pStmt.close();
        } catch (SQLException e) {
            log.error("insert sqlServer type error", e);
        } finally {
            assert connection != null;
            connection.close();
        }
    }

    private SQLServerDataTable buildSqlServerDataTable() throws SQLServerException {
        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        // Define metadata for the data table.
        sourceDataTable.addColumnMetadata("card_number", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("transaction_code", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("transaction_amount", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("message_type", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("approval_code", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("response_code", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("filler_field", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("account_type", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("approval_timestamp", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("retailer_id", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("sys_id", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("transaction_type", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("sequence_number", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("proxy_approval_flag", Types.VARCHAR);
        sourceDataTable.addColumnMetadata("archive_id", Types.BIGINT);
        return sourceDataTable;
    }

}
