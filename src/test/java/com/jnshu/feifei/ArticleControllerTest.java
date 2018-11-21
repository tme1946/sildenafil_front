package com.jnshu.feifei;

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
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=SildenafilFrontApplication.class)
public class ArticleControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Before // 在测试开始前初始化工作
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getBannerPageList() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/article/banner")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("page", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCardPageList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/article/card")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("page", "1")
                .param("size", "4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getArticle() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/a/u/front/article")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("articleId", "8")
                .param("studentId", "80")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void articleLike() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/a/u/front/article/like")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("articleId", "8")
                .param("studentId", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void articleCollection() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/a/u/front/article/collection")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("articleId", "8")
                .param("studentId", "6")
                .param("status", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}