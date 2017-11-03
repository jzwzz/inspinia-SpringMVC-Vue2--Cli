package com.cmb.ccd.mr.rtm.guardian.auth.service.impl;

import com.cmb.ccd.mr.rtm.guardian.auth.service.HttpCcmsService;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseConstants;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.entity.Employee;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j
public class HttpCcmsServiceImpl implements HttpCcmsService {

    @Value("${cas.server}")
    private String CAS_SERVER;
    @Value("${cas.api.login}")
    private String API_LOGIN;
    @Value("${cas.api.getUserDetail}")
    private String API_GET_USER_DETAIL;
    @Value("${cas.api.getUserByRoleId}")
    private String API_GET_USER_BY_ROLE;
    @Value("${cas.app.name}")
    private String systemId;

    @Override
    public ResponseResult login(String username, String password, String systemId) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("systemId", systemId);
        String encodingData = null;
        try {
            encodingData = Base64.encodeBase64String(new Gson().toJson(data).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = HttpClients.createDefault();

        String url = CAS_SERVER + API_LOGIN + encodingData;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse;
        Map<String, Object> resultMap;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");
        }
        String response;
        try {
            response = EntityUtils.toString(httpResponse.getEntity());
            resultMap = new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
            }.getType());

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
        HttpResponse httpResponse;
        String response;
        try {
            httpResponse = httpClient.execute(httpGet);
            response = EntityUtils.toString(httpResponse.getEntity());
            Employee employee = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create().fromJson(response, Employee.class);
            return new ResponseResult<>(employee);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult(ResponseConstants.REQUEST_FAILED, "请求CCMS异常");
        }
    }

    @Override
    public ResponseResult<List<User>> getUserByRoleId(String roleId) {
        HttpClient httpClient = HttpClients.createDefault();
        String url = CAS_SERVER + API_GET_USER_BY_ROLE + "&roleId=" + roleId;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse;
        String response;
        try {
            httpResponse = httpClient.execute(httpGet);
            response = EntityUtils.toString(httpResponse.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            String employees = objectMapper.readTree(response).get("employees").toString();
            List<User> employeeList = new Gson().fromJson(employees, new TypeToken<List<User>>() {
            }.getType());
            return new ResponseResult<>(employeeList);
        } catch (IOException e) {
            log.error(e);
            return new ResponseResult<>(ResponseConstants.REQUEST_FAILED, "请求CCMS异常", new ArrayList<User>());
        }
    }

}
