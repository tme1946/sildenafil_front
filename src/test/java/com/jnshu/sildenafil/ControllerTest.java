package com.jnshu.sildenafil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * #Title: ControllerTest
 * #ProjectName sildenafil_front
 * #Description: TODO
 * #author lihoo
 * #date 2018/11/18-10:01
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

    @Test
    public void video() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/video")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("page", "1")
//                .param("size", "4")
//                .param("grade", "4")
//                .param("subject", "9")
                .param("videoId","1")
                .param("studentId", "1")


                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }
}
