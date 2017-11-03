package com.cmb.ccd.mr.rtm.guardian.archive.form;

import lombok.Data;

@Data
public class ReviewForm {

    private long archiveId;
    private String reviewerId;
    private String reviewerName;
    private String opUserId;
    private String opUserName;
    private boolean accept;
    private String remark;
}
