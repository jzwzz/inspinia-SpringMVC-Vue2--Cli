package com.cmb.ccd.mr.rtm.guardian.hbase.service;

import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseRowVO;

import java.util.List;

public interface HBaseSearchService {

    List<HBaseRowVO> scan(String tableName, String startKey, String endKey, int scanLimit);

    List<HBaseRowVO> getRowsByRowKey(String tableName, List<String> rowKeyList);

    List<HBaseRowVO> getRowsByRowKeyAndColumn(String tableName, List<HBaseRowVO> rowViewList);
}
