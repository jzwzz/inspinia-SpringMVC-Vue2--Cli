package com.cmb.ccd.mr.rtm.guardian.archive.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageResultVO {

    private long failedRecords;
    private long successRecords;
    private long totalRecords;

    public void merge(SendMessageResultVO sendMessageResultVO) {
        this.failedRecords += sendMessageResultVO.failedRecords;
        this.successRecords += sendMessageResultVO.successRecords;
        this.totalRecords += sendMessageResultVO.totalRecords;
    }

}
