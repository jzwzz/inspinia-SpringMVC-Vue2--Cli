package com.armysheng.auth;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* * @Company: China Merchants Bank * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved. */
@ContextConfiguration({"classpath:spring/spring-mvc-config.xml", "classpath:spring/spring-security.xml"})
@WebAppConfiguration
public class SpringSecurityMvcTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void requestWithoutToken() throws Exception {
        mvc.perform(get("/user/unsafe").param("message", "hello").with(csrf()))
                .andExpect(status().isOk()).andDo(print());

    }

    //token info: id=671852,password="111",role="ROLE_RANDOM"
    @Test
    public void requestGetToken() throws Exception {
        mvc.perform(post("/user/login").param("username", "671852")
                .param("password", "111").param("role", "ROLE_RANDOM").with(csrf()))
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    public void requestWithToken() throws Exception {
        final String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6IjY3MTg1MiIsInBhc3N3b3JkIjoiMTExIiwicm9sZXMiOiJST0xFX1VTRVIifQ.4EO2rgJAEUUifj2ktU7Y40FeNIw987ZLOGWEIj-dU4OUlmmsbQWAz7TuVcCA6lGOF8aEWwgxL9YQytNLSdIPJg";
        mvc.perform(get("/user/security").param("message", "hello")
                .header("Authorization", "Bearer " + token).with(csrf()))
                .andExpect(status().isOk()).andDo(print());

    }
}
