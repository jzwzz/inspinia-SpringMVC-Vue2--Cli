package com.cmb.ccd.mr.rtm.guardian.kafka.message;

import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.cmb.ccd.mr.rtm.guardian.util.MD5Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class MessageBuilder {

    private static final String PREFIX = "guardian";
    private static final String DATE_TIME_PATTERN = "yyMMddHHmmss";
    @Value("${udr.event.attribute.file}")
    private String udrEventAttributeFile;
    private List<ValueRecord> rawValueRecordList;
    private ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_TIME_PATTERN));

    @PostConstruct
    public void init() throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url = ResourceUtils.getURL("classpath:" + udrEventAttributeFile);
        rawValueRecordList = objectMapper.readValue(url, new TypeReference<List<ValueRecord>>() {
        });
    }

    List<ValueRecord> buildValueRecordList(ArchiveDetail archiveDetail) {
        List<ValueRecord> valueRecordList = new ArrayList<>(rawValueRecordList.size());
        rawValueRecordList.forEach(rawValueRecord -> {
            ValueRecord valueRecord = new ValueRecord(rawValueRecord.getId(), rawValueRecord.getDataType(),
                    rawValueRecord.getValue(), rawValueRecord.getColumnName());
            valueRecordList.add(valueRecord);
        });

        Map<String, ValueRecord> valueRecordMap = valueRecordList.stream()
                .collect(Collectors.toMap(ValueRecord::getColumnName, valueRecord -> valueRecord));
        valueRecordMap.get("account_type").setValue(archiveDetail.getAccountType());
        valueRecordMap.get("approval_code").setValue(archiveDetail.getApprovalCode());
        valueRecordMap.get("approval_timestamp").setValue(archiveDetail.getApprovalTimestamp());
        valueRecordMap.get("approval_handle_date").setValue("20" + archiveDetail.getApprovalTimestamp().substring(0, 6));
        valueRecordMap.get("card_number").setValue(archiveDetail.getCardNumber());
        valueRecordMap.get("filler_field").setValue(archiveDetail.getFillerField());
        valueRecordMap.get("message_type").setValue(archiveDetail.getMessageType());
        valueRecordMap.get("response_code").setValue(archiveDetail.getResponseCode());
        valueRecordMap.get("retailer_id").setValue(archiveDetail.getRetailerId());
        valueRecordMap.get("proxy_approval_flag").setValue(archiveDetail.getProxyApprovalFlag());
        valueRecordMap.get("sequence_number").setValue(archiveDetail.getSequenceNumber());
        valueRecordMap.get("sys_id").setValue(archiveDetail.getSysId());
        valueRecordMap.get("transaction_amount").setValue((archiveDetail.getTransactionAmount()));
        valueRecordMap.get("transaction_code").setValue(archiveDetail.getTransactionCode());
        valueRecordMap.get("transaction_type").setValue(archiveDetail.getTransactionType());
        return valueRecordList;
    }

    public JsonMessage buildJsonMessage(ArchiveDetail archiveDetail) throws ParseException {
        JsonMessage jsonMessage = new JsonMessage();
        List<ValueRecord> valueRecordList = buildValueRecordList(archiveDetail);
        jsonMessage.setAttributes(valueRecordList.stream().collect(Collectors.toMap(ValueRecord::getId, valueRecord -> valueRecord)));
        SimpleDateFormat simpleDateFormat = dateFormatThreadLocal.get();
        jsonMessage.setBusinessTime(simpleDateFormat.parse(archiveDetail.getApprovalTimestamp()));
        jsonMessage.setMessageId(getMessageId(valueRecordList, archiveDetail.getApprovalTimestamp()));
        jsonMessage.setReceiveTime(new Date());
        jsonMessage.setSysId(archiveDetail.getSysId());
        jsonMessage.setTopic("RTM-Tand");
        return jsonMessage;
    }

    private String getMessageId(List<ValueRecord> valueRecordList, String approvalTimestamp) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ValueRecord valueRecord : valueRecordList) {
            stringBuilder.append(valueRecord.getValue());
        }
        return "20" + approvalTimestamp + PREFIX + MD5Util.getMD5(stringBuilder.toString());
    }
}
