package com.cmb.ccd.mr.rtm.guardian.hbase.view;

import lombok.Data;

import java.util.List;

@Data
public class HBaseRowVO {

    private String rowKey;
    private List<HBaseColumnVO> columnViewList;
}
