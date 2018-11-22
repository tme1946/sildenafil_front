package com.jnshu.L;

import com.jnshu.sildenafil.SildenafilFrontApplication;
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

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=SildenafilFrontApplication.class)
public class SignControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private org.springframework.web.context.WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getSignList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/a/u/front/sign")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("studentId", "1")
//                .param("size", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void sign() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/sign")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("studentId", "1")
//                .param("size", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void cola() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/a/u/front/video/collection")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("studentId", "1")
                .param("videoId", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void colb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/a/u/front/video/collection")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("studentId", "1")
                .param("videoId", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void like() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/a/u/front/video/like")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("studentId", "1")
                .param("videoId", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void te() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/admin/teacher/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("studentId", "1")
//                .param("videoId", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/admin/teacher")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("teacherId", "1")
//                .param("videoId", "5")
//                .param("grade","4")
//                .param("subject","9")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}