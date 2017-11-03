package com.cmb.ccd.mr.rtm.guardian.kafka.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class JsonMessage {

    private String messageId;
    private String sysId;
    private String topic;

    //消息业务时间
    private Date businessTime;
    //系统接收时间
    private Date receiveTime;
    private Map<String, ValueRecord> attributes;

    // rtm tags and udr tags are all in udr tags
    private Map<String, ValueRecord> udrTags = new HashMap<>();

}
