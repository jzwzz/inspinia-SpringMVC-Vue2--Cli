package com.cmb.ccd.mr.rtm.guardian.hbase.service;

import com.cmb.ccd.mr.common.hbase.thrift.HBaseCell;
import com.cmb.ccd.mr.common.hbase.thrift.HBaseRow;
import com.cmb.ccd.mr.common.hbase.thrift.HBaseThriftHelper;
import com.cmb.ccd.mr.common.hbase.thrift.ThriftClientPool;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseColumnVO;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseRowVO;
import lombok.extern.log4j.Log4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class HBaseSearchServiceImpl implements HBaseSearchService {

    @Autowired
    private ThriftClientPool thriftClientPool;
    @Value("${hbase.thrift.cache.size}")
    private int catchSize;

    @Override
    public List<HBaseRowVO> scan(String tableName, String startKey, String endKey, int scanLimit) {
        List<HBaseRow> rowList = new ArrayList<>();
        try {
            List<HBaseRow> hBaseRows = thriftClientPool.scanRows(tableName, startKey, endKey, catchSize, scanLimit);
            log.info("HBase scan " + hBaseRows.size() + " row");
            rowList.addAll(hBaseRows);
        } catch (TException e) {
            log.error(e.getMessage());
        }
        return parseHBaseRowList(rowList);
    }

    @Override
    public List<HBaseRowVO> getRowsByRowKey(String tableName, List<String> rowKeyList) {
        List<HBaseRow> hBaseRowList = new ArrayList<>();
        try {
            hBaseRowList = thriftClientPool.getRows(tableName, rowKeyList);
            log.info("HBase get " + hBaseRowList.size() + " row");
        } catch (TException e) {
            log.error(e.getMessage());
        }
        return parseHBaseRowList(hBaseRowList);
    }

    /**
     * throw TException when get 0 message
     */
    @Override
    public List<HBaseRowVO> getRowsByRowKeyAndColumn(String tableName, List<HBaseRowVO> rowViewList) {
        List<HBaseRow> hBaseRowList = new ArrayList<>();
        for (HBaseRowVO rowView : rowViewList) {
            List<HBaseCell> hBaseCellList = new ArrayList<>();
            for (HBaseColumnVO columnView : rowView.getColumnViewList()) {
                try {
                    HBaseCell hBaseCell = HBaseThriftHelper.constructCell(rowView.getRowKey(), columnView.getColumnFamily(),
                            columnView.getColumnName(), "");
                    hBaseCellList.add(hBaseCell);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
            }
            HBaseRow hBaseRow = new HBaseRow(hBaseCellList);
            hBaseRowList.add(hBaseRow);
        }

        List<HBaseRow> resultRowList = new ArrayList<>();
        try {
            resultRowList = thriftClientPool.getRowsByColumns(tableName, hBaseRowList);
            log.info("HBase get " + resultRowList.size() + " row");
        } catch (TException e) {
            log.error(e.getMessage());
        }
        return parseHBaseRowList(resultRowList);
    }

    private List<HBaseRowVO> parseHBaseRowList(List<HBaseRow> hBaseRowList) {
        List<HBaseRowVO> rowViewList = new ArrayList<>();
        for (HBaseRow hBaseRow : hBaseRowList) {
            if (hBaseRow.getCells() == null || hBaseRow.getCells().isEmpty()) {
                continue;
            }
            String rowKey = new String(hBaseRow.getCells().get(0).getRowkey());
            HBaseRowVO hBaseRowVO = new HBaseRowVO();
            hBaseRowVO.setRowKey(rowKey);
            List<HBaseColumnVO> columnViewList = new ArrayList<>();
            for (HBaseCell hBaseCell : hBaseRow.getCells()) {
                String columnName = new String(hBaseCell.getColumn());
                String value = new String(hBaseCell.getValue());
                String columnFamily = new String(hBaseCell.getFamily());
                HBaseColumnVO columnView = new HBaseColumnVO(columnName, value, columnFamily);
                columnViewList.add(columnView);
            }
            hBaseRowVO.setColumnViewList(columnViewList);
            rowViewList.add(hBaseRowVO);
        }
        return rowViewList;
    }
}
