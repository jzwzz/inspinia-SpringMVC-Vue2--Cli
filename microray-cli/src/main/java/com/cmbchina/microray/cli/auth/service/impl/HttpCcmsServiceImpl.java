package com.cmbchina.microray.cli.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.cmbchina.microray.cli.auth.service.HttpCcmsService;
import com.cmbchina.microray.cli.common.ResponseConstants;
import com.cmbchina.microray.cli.common.ResponseResult;
import com.cmbchina.microray.cli.user.entity.Employee;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Service
@Log4j


public class HttpCcmsServiceImpl implements HttpCcmsService {
    @Value("${cas.server}")
    private String CAS_SERVER;
    @Value("${cas.api.login}")
    private String API_LOGIN;
    @Value("${cas.api.getUserDetail}")
    private String API_GET_USER_DETAIL;
    @Value("${cas.page.login}")
    private String PAGE_LOGIN;
    @Value("${app.name}")
    private String systemId;


    @Override
    public ResponseResult login(String username, String password) {


        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("systemId", systemId);
        String encodingData = null;
        try {
            encodingData = Base64.encodeBase64String(JSON.toJSONString(data).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = HttpClients.createDefault();

        String url = CAS_SERVER + API_LOGIN + encodingData;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");
        }
        String response;
        try {
            response = EntityUtils.toString(httpResponse.getEntity());
            resultMap = JSON.parseObject(response);

            if (resultMap.get("Code").equals("000")) {
                Employee employee = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create().fromJson(response, Employee.class);
                return new ResponseResult<>(employee);
            }
            return new ResponseResult(resultMap.get("Code").toString(), resultMap.get("Msg").toString());
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");
        }
    }

    @Override
    public ResponseResult getEmployeeInfoById(String employeeId) {
        HttpClient httpClient = HttpClients.createDefault();
        String url = CAS_SERVER + API_GET_USER_DETAIL + "&employeeId=" + employeeId + "&systemId=" + systemId;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");
        }
        String response;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            response = EntityUtils.toString(httpResponse.getEntity());
            resultMap = JSON.parseObject(response);
            Employee employee = new GsonBuilder().create().fromJson(response, Employee.class);
            return new ResponseResult<>(employee);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");

        }
    }

    @Override
    public String getRedirectPath(String redirectUrl) {
//        http://99.48.237.206:8086/mr-cas/login?service=http://99.48.6.207:9080#/redirectLogin
        return CAS_SERVER + PAGE_LOGIN + redirectUrl;
    }

}
