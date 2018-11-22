package com.jnshu.L;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * #Title: ControllerTest
 * #ProjectName sildenafil_front
 * #Description: TODO
 * #author lihoo
 * #date 2018/11/22-11:27
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private org.springframework.web.context.WebApplicationContext webApplicationContext;
    @Before // 在测试开始前初始化工作
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }






}
