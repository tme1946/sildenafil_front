package com.jnshu.sildenafil;

import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.jnshu.sildenafil.system.service.VideoService;
import com.jnshu.sildenafil.util.EmailUtil;
import com.jnshu.sildenafil.util.ShortMassage;
import com.jnshu.sildenafil.util.VerifyCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SildenafilFrontApplicationTests {
    @Autowired
    private VideoService videoService;
    @Autowired
    CollectionAssetService collectionAssetService;
    @Test
    public void contextLoads() {
        List idList = collectionAssetService.collectiongListByStudent(1,1l);
        for (Object o : idList) {
            System.out.println(o);
        }

    }

    @Test
    public void videoTest() {
        String code = VerifyCode.numbers(5);
        System.out.println(ShortMassage.singleSend("16600264020", code));
    }
}
