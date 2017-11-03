package com.cmb.ccd.mr.rtm.guardian.hbase.controller;

import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.hbase.service.HBaseSearchService;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseColumnVO;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("hbase")
public class HBaseController {

    @Autowired
    private HBaseSearchService searchService;

    @GetMapping("scan")
    public ResponseResult<List<HBaseRowVO>> scan(String tableName, String startKey, String endKey, int limit) {
        if (limit > 5000) {
            limit = 5000;
        }
        List<HBaseRowVO> rowViewList = searchService.scan(tableName, startKey, endKey, limit);
        return new ResponseResult<>(rowViewList);
    }

    @GetMapping("get")
    public ResponseResult<List<HBaseRowVO>> get(String tableName, @RequestParam(value = "rowKeyList") ArrayList<String> rowKeyList) {
        List<HBaseRowVO> rowViewList = searchService.getRowsByRowKey(tableName, rowKeyList);
        return new ResponseResult<>(rowViewList);
    }

    @GetMapping("getRowsByRowKeyAndColumn")
    public ResponseResult<List<HBaseRowVO>> getRowsByRowKeyAndColumn(String tableName, String rowKey, String columnFamily, String columnName) {
        HBaseColumnVO hBaseColumnVO = new HBaseColumnVO(columnName, "", columnFamily);
        HBaseRowVO hBaseRowVO = new HBaseRowVO();
        hBaseRowVO.setRowKey(rowKey);
        hBaseRowVO.setColumnViewList(Collections.singletonList(hBaseColumnVO));
        List<HBaseRowVO> rowViewList = searchService.getRowsByRowKeyAndColumn(tableName, Collections.singletonList(hBaseRowVO));
        return new ResponseResult<>(rowViewList);
    }

}
