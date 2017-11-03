package com.cmb.ccd.mr.rtm.guardian.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by yanfei on 2/9/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueRecord implements Serializable {

    private String id;
    private DataTypeEnum dataType;
    private Object value;
    private transient String columnName;

}
