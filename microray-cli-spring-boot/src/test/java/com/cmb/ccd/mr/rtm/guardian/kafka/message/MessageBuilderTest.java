package com.cmb.ccd.mr.rtm.guardian.kafka.message;

import com.cmb.ccd.mr.rtm.guardian.AnnotationBaseTest;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

public class MessageBuilderTest extends AnnotationBaseTest {

    @Autowired
    private MessageBuilder messageBuilder;
    @Value("${udr.event.attribute.file}")
    private String udrEventAttributeFile;

    @Test
    public void testLoadJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url = ResourceUtils.getURL("classpath:" + udrEventAttributeFile);
        List<ValueRecord> valueRecordList = objectMapper.readValue(url, new TypeReference<List<ValueRecord>>() {
        });
        System.out.println(valueRecordList);
        long start = System.currentTimeMillis();
        for (ValueRecord valueRecord : valueRecordList) {
            BeanMap beanMap = BeanMap.create(valueRecord);
            System.out.println(beanMap);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void buildValueRecordList() {
        String line = "3568891120673678|~|10|~|1230|~|0200|~|231099|~|001|~|0|~|1|~|Z2007933000010|~|0100166566|~|99|~|170904103053|~|00B05605004I|~|00";
        ArchiveDetail archiveDetail = new ArchiveDetail(line);
        List<ValueRecord> valueRecordList = messageBuilder.buildValueRecordList(archiveDetail);
        Assert.assertEquals(15, valueRecordList.size());

    }

    @Test
    public void buildJsonMessage() throws ParseException {
        String line = "3568891120673678|~|10|~|1230|~|0200|~|231099|~|001|~|0|~|1|~|Z2007933000010|~|0100166566|~|99|~|170904103053|~|00B05605004I|~|00";
        ArchiveDetail archiveDetail = new ArchiveDetail(line);
        archiveDetail.setAccountType("001");
        archiveDetail.setMessageType("0220");
        JsonMessage jsonMessage = messageBuilder.buildJsonMessage(archiveDetail);
        Assert.assertEquals(15, jsonMessage.getAttributes().size());
        Assert.assertEquals("3568891120673678", jsonMessage.getAttributes().get("E6JDSHwoHJ5odxmZ").getValue());
        Assert.assertEquals("231099", jsonMessage.getAttributes().get("GdQuuG86o8i2mPvu").getValue());
        Assert.assertEquals("Z2007933000010", jsonMessage.getAttributes().get("PKQ6C6IssOIri2EM").getValue());
        Assert.assertEquals("00B05605004I", jsonMessage.getAttributes().get("pVnw8Fr0NePEIqDW").getValue());
        Assert.assertEquals("1230", jsonMessage.getAttributes().get("SddHvAV3ibRHpxcE").getValue());
        Assert.assertEquals("00", jsonMessage.getAttributes().get("xMR42cJS6geVSJ1y").getValue());
        System.out.println(new Gson().toJson(jsonMessage));
    }

}