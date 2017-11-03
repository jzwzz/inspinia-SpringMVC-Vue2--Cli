/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.workflow.entity;

import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Workflow {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Workflow.OpType opType;
    private String opUserId;
    private String opUserName;
    private Date opDateTime;
    private String remark;
    @ManyToOne
    @JoinColumn(name = "archive_id")
    @JsonIgnore
    private Archive archive;

    public Workflow(OpType opType, String opUserId, String opUserName, String remark) {
        this.opType = opType;
        this.opUserId = opUserId;
        this.opUserName = opUserName;
        this.opDateTime = new Date();
        this.remark = remark;
    }

    public enum OpType {
        审核, 放行, 作废
    }
}
