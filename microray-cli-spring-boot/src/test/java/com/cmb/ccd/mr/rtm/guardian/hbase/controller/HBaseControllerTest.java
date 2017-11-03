package com.cmb.ccd.mr.rtm.guardian.hbase.controller;

import com.cmb.ccd.mr.rtm.guardian.AnnotationBaseTest;
import com.cmb.ccd.mr.rtm.guardian.auth.Credentials;
import com.cmb.ccd.mr.rtm.guardian.auth.JwtUtil;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Log4j
public class HBaseControllerTest extends AnnotationBaseTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private JwtUtil jwtUtil;
    private String jwtToken;
    @Value("{jwt.header}")
    private String requestHeaderName;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        Credentials credentials = new Credentials();
        credentials.setRoles("ROLE_USER");
        credentials.setPassword("aaa");
        credentials.setUsername("aaa");
        jwtToken = jwtUtil.generateToken(credentials);
    }

    @Test
    public void scan() throws Exception {
        String url = "http://localhost:8080/hbase/scan?tableName=ams:message_detail_junit&startKey=&endKey=&limit=1";
        RequestBuilder request = MockMvcRequestBuilders.get(url).header(requestHeaderName, jwtToken);
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertTrue("正确", status == 200);
        String content = mvcResult.getResponse().getContentAsString();
        log.info("content:" + content);
    }

    @Test
    public void get() throws Exception {
        String url = "http://localhost:8080/hbase/get?tableName=ams:message_detail_junit&rowKeyList=1&rowKeyList=1";
        RequestBuilder request = MockMvcRequestBuilders.get(url).header(requestHeaderName, jwtToken);
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertTrue("正确", status == 200);
        String content = mvcResult.getResponse().getContentAsString();
        log.info("content:" + content);
    }

}
