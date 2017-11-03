/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class ArchiveDetail {

    @Id
    @GeneratedValue
    private Long id;
    private Long archiveId;
    private String accountType;
    private String approvalCode;
    private String approvalTimestamp;
    private String cardNumber;
    private String fillerField;
    private String messageType;
    private String responseCode;
    private String retailerId;
    private String proxyApprovalFlag;
    private String sequenceNumber;
    private String sysId;
    private String transactionAmount;
    private String transactionCode;
    private String transactionType;

    public ArchiveDetail(String fileLine) {
        String[] element = fileLine.split("\\|~\\|");
        if (element.length < 14) {
            throw new IllegalArgumentException("the size of content args array is less than 14");
        }

        this.cardNumber = element[0];
        this.transactionCode = element[1];
        this.transactionAmount = element[2];
        this.messageType = element[3];
        this.approvalCode = element[4];
        this.responseCode = element[5];
        this.fillerField = element[6];
        this.accountType = element[7];
        this.retailerId = element[8];
        this.sysId = element[9];
        this.transactionType = element[10];
        this.approvalTimestamp = element[11];
        this.sequenceNumber = element[12];
        this.proxyApprovalFlag = element[13];
    }
}
