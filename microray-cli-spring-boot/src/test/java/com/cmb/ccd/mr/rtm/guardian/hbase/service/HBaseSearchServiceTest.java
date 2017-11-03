package com.cmb.ccd.mr.rtm.guardian.hbase.service;

import com.cmb.ccd.mr.rtm.guardian.AnnotationBaseTest;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseColumnVO;
import com.cmb.ccd.mr.rtm.guardian.hbase.view.HBaseRowVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class HBaseSearchServiceTest extends AnnotationBaseTest {

    @Autowired
    private HBaseSearchService searchService;

    @Test
    public void scan() {
        List<HBaseRowVO> rowViewList = searchService.scan("ams:message_detail_junit", "0100166504guardian", "", 1);
        System.out.println(rowViewList);
    }

    @Test
    public void getRowsByRowKeyAndColumn() {
        HBaseRowVO hBaseRowVO = new HBaseRowVO();
        hBaseRowVO.setRowKey("0100166504guardian-03e16268ffb028b42e1d37accc98e4c6");
        hBaseRowVO.setColumnViewList(Collections.singletonList(new HBaseColumnVO("", "", "FC")));
        List<HBaseRowVO> rowViewList = searchService.getRowsByRowKeyAndColumn("ams:message_detail_junit",
                Collections.singletonList(hBaseRowVO));
        System.out.println(rowViewList);
    }
}