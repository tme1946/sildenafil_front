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
public class VideoServiceImplTest {
    private MockMvc mockMvc;
    @Autowired
    private org.springframework.web.context.WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void getPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/video/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("page", "1")
                .param("size", "4")
//                .param("type","1")
//                .param("typeId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getVideoById() {
    }

    @Test
    public void updateLikeAmount() {
    }

    @Test
    public void updateCollectionAmount() {
    }

    @Test
    public void getBannerList() {
    }
}