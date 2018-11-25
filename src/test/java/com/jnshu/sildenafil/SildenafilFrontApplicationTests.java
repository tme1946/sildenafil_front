package com.jnshu.sildenafil;

import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.jnshu.sildenafil.system.service.VideoService;
import com.jnshu.sildenafil.util.EmailUtil;
import com.jnshu.sildenafil.util.RedisUtil;
import com.jnshu.sildenafil.util.ShortMassage;
import com.jnshu.sildenafil.util.VerifyCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
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
    public void contextLoads() throws Exception {
        List idList = collectionAssetService.studentCollection(1,1l);
        for (Object o : idList) {
            System.out.println(o);
        }

    }
@Autowired
RedisUtil redisUtil;
    @Test
    public void phoneTest() {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        Object value = redisTemplate.opsForValue().get("16600264020");
        Object value = redisUtil.get("16600264020");
        System.out.println(value);
        System.out.println(value.equals("60980"));
    }
}
