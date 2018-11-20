package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Article;
import com.jnshu.sildenafil.system.service.ArticleService;
import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.jnshu.sildenafil.system.service.LikeAssetService;

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
    private LikeAssetService likeAssetService;
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

    /**根据文章id和studentId查询文章详情
     * @param articleId 文章id
     * @param studentId 学生id
     * @return 文章列表
     */
    @GetMapping(value = "/a/u/front/article")
    public ResponseBo getArticle(Long articleId,Long studentId) throws Exception{
        log.info("args for getArticle is : articleId={}&studentId={}",articleId,studentId);
        Article article=articleService.getArticleById(articleId);
        if(null!=article){
            //查询点赞收藏状态
            int collectionStatus=collectionAssetService.selectCollection(0,articleId,studentId);
            int likeStatus=likeAssetService.selectLike(0,articleId,studentId);
            return ResponseBo.ok()
                    .put("article",article)
                    .put("likeStatus",likeStatus)
                    .put("collectionStatus",collectionStatus);
        }
        log.error("结果为空");
        return ResponseBo.error("结果异常");
    }

    /**根据文章id对文章点赞
     * @param articleId 文章id
     * @param studentId 学生id
     * @return 对应文章
     */
    @PostMapping(value = "/a/u/front/article/like")
    public ResponseBo articleLike(Long articleId,Long studentId) throws Exception {
        log.info("args for insertLike is : typeId={}&studentId={}&",articleId,studentId);
        Long likeId=likeAssetService.insertLike(0,articleId,studentId);
        if(null!=likeId){
            return ResponseBo.ok();
        }
        log.error("结果为null");
        return ResponseBo.error("结果异常");
    }

    /**前台对文章进行收藏状态改变
     * @param articleId 文章id
     * @param studentId 学生id
     * @return 返回收藏的结果
     */
    @PutMapping(value = "/a/u/front/article/collection")
    public ResponseBo articleCollection(Long articleId,Long studentId,Integer status) throws Exception {
        log.info("args for insertCollection is :articleId={}&studentId={}&",articleId,studentId);
        Long typeId2;
        if(null!=status && 1==status) {
            //进行点赞
            typeId2 = collectionAssetService.insertCollection(0, articleId, studentId);
        }else{
            //取消点赞
            typeId2 = collectionAssetService.removeCollection(0, articleId, studentId);
        }
        if(null!=typeId2){
            return ResponseBo.ok("操作成功");
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
