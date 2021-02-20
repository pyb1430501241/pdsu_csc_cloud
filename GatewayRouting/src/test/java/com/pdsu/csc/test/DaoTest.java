package com.pdsu.csc.test;

import com.pdsu.csc.service.UserInformationService;
import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 23:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {

    @Autowired
    UserInformationService userInformationService;

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @SuppressWarnings("all")
    @Autowired
    org.apache.shiro.mgt.SecurityManager securityManager;

    @Before
    public void initMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void test() throws Exception {
        System.out.println(userInformationService.selectByUid(181360226));
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user/getcodeforlogin"))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        String[] split = response.getContentAsString().replaceAll("[{}:,]", "").split("\"");
        List<String> list = new ArrayList<>(Arrays.asList(split));
        list.removeAll(Arrays.asList(""));
        list.remove("json");
        MultiValueMap<String, String> t = new LinkedMultiValueMap<String, String>();
        t.add("uid", "181360226");
        t.add("password", "pyb***20000112");
        t.add("hit", list.get(list.indexOf("token") + 1));
        t.add("code", list.get(list.indexOf("vicode") + 1));
        t.add("flag", "1");
        result = mvc.perform(MockMvcRequestBuilders.post("/user/login").params(t)).andReturn();
        response = result.getResponse();
        response.setCharacterEncoding("UTF-8");
        System.out.println(response.getContentAsString());
    }

}
