package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Article;
import com.jnshu.sildenafil.system.service.ArticleService;
import com.jnshu.sildenafil.system.service.CollectionAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feifei
 */
@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CollectionAssetService collectionAssetService;
    /**前台分页查询banner文章信息
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    @GetMapping(value = "/a/u/front/article/banner")
    public ResponseBo getBannerPageList(Integer page, Integer size)throws Exception{
        log.info("args for getBannerList is : page={}&size={}",page,size);
        List bannerList= articleService.getFrontBannerPageList(1,2);
        if(bannerList!=null){
            return ResponseBo.ok("请求成功").put("bannerList",bannerList);
        }
        log.error("结果为空");
        return ResponseBo.error("参数异常");
    }

    /**前台分页查询card文章信息
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    @GetMapping(value = "/a/u/front/article/card")
    public ResponseBo getCardPageList(Integer page, Integer size) throws Exception{
        log.info("args for getCardPageList is : page={}&size={}",page,size);
        IPage cardPageList=articleService.getFrontCardPageList(page,size);
        if(cardPageList!=null){
            return ResponseBo.ok().put("cardPageList",cardPageList);
        }
        log.error("结果为空");
        return ResponseBo.error("结果异常");
    }

    /**根据文章id对文章点赞
     * @param articleId 文章id
     * @return 对应文章
     */
    @PostMapping(value = "/a/u/front/article/like")
    public ResponseBo getArticleById(Long articleId) throws Exception {
        log.info("args for getArticleById : articleId=[{}]",articleId);
        Article article=articleService.getArticleById(articleId);
        if(null!=article){
            return ResponseBo.ok();
        }
        log.error("结果为null");
        return ResponseBo.error("结果异常");
    }
    /**
     * 学生文章收藏列表
     * @param [studentId]
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("学生文章收藏列表")
    @ResponseBody
    @GetMapping(value = "/a/u/front/article/collection/student")
    public ResponseBo collectionByStudent(Long studentId) throws Exception{
        List<Long> typeIdList =collectionAssetService.collectiongListByStudent(0,studentId);
        List<Article> typeList = typeIdList.stream().map(id ->articleService.getById(id)).collect(Collectors.toList());
        return ResponseBo.ok().put("data", typeList);
    }
}
