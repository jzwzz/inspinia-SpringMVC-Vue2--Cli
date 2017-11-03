package com.cmb.ccd.mr.rtm.guardian.auth.service.impl;

import com.cmb.ccd.mr.rtm.guardian.AnnotationBaseTest;
import com.cmb.ccd.mr.rtm.guardian.auth.service.HttpCcmsService;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.entity.Employee;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HttpCcmsServiceImplTest extends AnnotationBaseTest {

    @Autowired
    private HttpCcmsService httpCcmsService;

    @Test
    public void getUserByRoleId() throws Exception {
        ResponseResult<List<User>> responseResult = httpCcmsService.getUserByRoleId("RMG02");
        int size = responseResult.getData().size();
        Assert.assertNotEquals(0, size);
    }

}