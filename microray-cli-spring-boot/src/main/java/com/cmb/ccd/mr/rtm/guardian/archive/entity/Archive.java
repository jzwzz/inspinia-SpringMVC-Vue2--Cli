/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.entity;

import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import com.cmb.ccd.mr.rtm.guardian.workflow.entity.Workflow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Archive {

    public static final String DEFAULT_CREATOR_NAME = "system";
    public static final String DEFAULT_CREATOR_ID = "-123456";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    @Temporal(TemporalType.DATE)
    private Date batchDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDateTime;
    private String creatorId;
    private String creatorName;
    private String reviewerId;
    private String reviewerName;
    private Long size;
    @Enumerated(EnumType.STRING)
    private ArchiveStatus status;

    @OneToMany(mappedBy = "archive")
    @JsonIgnore
    private List<Workflow> workflowList;

    public Archive(String fileName, User user) {
        this.fileName = fileName;
        try {
            this.batchDate = dateFormat.parse(fileName.split("_")[5]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.createDateTime = new Date();

        this.creatorName = user.getEmployeeName();
        this.creatorId = user.getEmployeeId();
        this.status = ArchiveStatus.待审核;
    }
}
