package com.jnshu.feifei;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jnshu.sildenafil.SildenafilFrontApplication;
import com.jnshu.sildenafil.system.domain.LikeAsset;
import com.jnshu.sildenafil.system.mapper.LikeAssetDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=SildenafilFrontApplication.class)
public class ArticleServiceImplTest {
    @Autowired
    private LikeAssetDao likeAssetDao;
    @Test
    public void getFrontCardPageList() {
    }

    @Test
    public void getFrontBannerPageList() {
    }

    @Test
    public void getArticleById() {
    }

    @Test
    public void updateArticleLikeAmount() {
        QueryWrapper<LikeAsset> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",0).eq("type_id",null);
        System.out.println(likeAssetDao.selectCount(queryWrapper));
    }
}