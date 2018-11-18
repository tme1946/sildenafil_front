package com.jnshu.sildenafil;

import com.jnshu.sildenafil.system.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SildenafilFrontApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private VideoService videoService;
    @Test
    public void videoTest() {
        videoService.getBannerList(1,7).getRecords().forEach(System.out::println);
//        videoService.getPage(1,5,4,null).getRecords().forEach(System.out::println);
    }
}
