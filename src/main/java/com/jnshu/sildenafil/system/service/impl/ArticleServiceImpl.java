package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.exception.ServiceException;
import com.jnshu.sildenafil.system.domain.Article;
import com.jnshu.sildenafil.system.domain.CollectionAsset;
import com.jnshu.sildenafil.system.domain.LikeAsset;
import com.jnshu.sildenafil.system.mapper.ArticleDao;
import com.jnshu.sildenafil.system.mapper.CollectionAssetDao;
import com.jnshu.sildenafil.system.mapper.LikeAssetDao;
import com.jnshu.sildenafil.system.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jnshu.sildenafil.system.service.LikeAssetService;
import com.jnshu.sildenafil.util.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feifei
 * @since 2018-11-11
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired(required = false)
    private ArticleDao articleDao;
    @Autowired
    private LikeAssetDao likeAssetDao;
    @Autowired
    private CollectionAssetDao collectionAssetDao;
    /**前台分页查询card文章信息
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    @Override
    public IPage getFrontCardPageList(Integer page, Integer size){
        log.info("args for getFrontPageList is : page={}&size={}",page,size);
        //调整page和size默认值--
        page= null==page||page<=1 ? 1 : page;
        size= null==size||size<=1||size>20 ? 10 : size;
        IPage<Article> pageQuery=new MyPage<Article>(page,size).setDesc("update_at");
        //构建条件查询语句
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id","type","title","author","cover","digest","like_amount","collection_amount","create_at")
                .eq("type",0)
                .eq("status",1);
        IPage articleCardIPage=articleDao.selectPage( pageQuery,queryWrapper);
        if(articleCardIPage.getRecords().size()>0)
        {
            log.info("result for getFrontCardPageList's size is {}",articleCardIPage.getRecords().size());
            return articleCardIPage;
        } else{
            log.error("result for getFrontCardPageList error :***reason is list null***");
            return null;
        }
    }

    /**前台查询banner文章信息,不用分页
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    @Override
    public List getFrontBannerPageList(Integer page, Integer size){
        log.info("args for getFrontPageList is : page={}&size={}",page,size);
        //调整page和size默认值--
        page= null==page||page<=1? 1 : page;
        size= null==size||size<=1||size>20 ? 10 : size;
        IPage<Article> pageQuery=new MyPage<Article>(page,size).setDesc("update_at");
        //构建条件查询语句
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id","type","title","author","cover","digest","like_amount","collection_amount","create_at")
                .eq("type",1)
                .eq("status",1);
        IPage articleBannerIPage=articleDao.selectPage( pageQuery,queryWrapper);
        if(articleBannerIPage.getRecords().size()>0)
        {
            log.info("result for getFrontBannerPageList's size is {}",articleBannerIPage.getRecords().size());
            return articleBannerIPage.getRecords();
        } else{
            log.error("result for getFrontBannerPageList error :***reason is list null***");
            return null;
        }
    }

    /**前台根据文章id查询文章信息
     * @param articleId 文章id
     * @exception ServiceException 自定异常,参数错误
     * @return 对应文章
     */
    @Override
    public  Article getArticleById(Long articleId) throws ServiceException {
        log.info("args for getArticleById is : articleId={}",articleId);
        if (null == articleId) {
            log.error("result for getArticleById error;args is null");
            throw new ServiceException("getArticleById args is null");
        }else{
            //构建条件查询语句
            QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
            queryWrapper.select("id","type","title","author","cover","body","like_amount","collection_amount","create_at")
                    .eq("id",articleId);
            Article article=articleDao.selectOne(queryWrapper);
            if(null==article){
                log.error("result for getArticleById error;articleId not exist");
                return null;
            }
            log.info("result for getArticleById is :{}",article);
            return article;
        }
    }
    /**前台根据文章id修改文章点赞数
     * @param articleId 文章id
     * @throws ServiceException 自定义异常，参数错误
     * @return 对应文章id
     */
    @Override
    public Long updateArticleLikeAmount(Long articleId) throws ServiceException{
        log.info("args for updateArticleLikeAmount is : articleId={}",articleId);
        if (null == articleId) {
            log.error("result for updateArticleLikeAmount error;args is null");
            throw new ServiceException("updateArticleLikeAmount args is null");
        }else{
            //构建id查询文章点赞的数量
            QueryWrapper<LikeAsset> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("type",0).eq("type_id",articleId);
            int likeAmount=likeAssetDao.selectCount(queryWrapper);
            log.info("result for updateArticleLikeAmount likeAmount=[{}]",likeAmount);
            Article article=new Article();
            article.setLikeAmount(likeAmount);
            article.setId(articleId);
            articleDao.updateById(article);
            return  articleId;
        }
    }

    /**前台根据文章id修改文章收藏数
     * @param articleId 文章id
     * @throws ServiceException 自定义异常，参数错误
     * @return 对应文章id
     */
    @Override
    public Long updateArticleCollectionAmount(Long articleId) throws ServiceException{
        log.info("args for updateArticleCollectionAmount is : articleId={}",articleId);
        if (null == articleId) {
            log.error("result for updateArticleCollectionAmount error;args is null");
            throw new ServiceException("updateArticleCollectionAmount args is null");
        }else{
            //构建id查询文章点赞的数量
            QueryWrapper<CollectionAsset> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("type",0).eq("type_id",articleId);
            int collectionAmount=collectionAssetDao.selectCount(queryWrapper);
            log.info("result for updateArticleCollectionAmount collectionAmount=[{}]",collectionAmount);
            Article article=new Article();
            article.setCollectionAmount(collectionAmount);
            article.setId(articleId);
            articleDao.updateById(article);
            return  articleId;
        }
    }
}
