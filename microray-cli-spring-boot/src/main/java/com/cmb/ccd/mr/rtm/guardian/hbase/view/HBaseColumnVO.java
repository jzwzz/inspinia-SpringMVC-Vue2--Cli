package com.cmb.ccd.mr.rtm.guardian.hbase.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HBaseColumnVO {

    private String columnName;
    private String value;
    private String columnFamily;
}
