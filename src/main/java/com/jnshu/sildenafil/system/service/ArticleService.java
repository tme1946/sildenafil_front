package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.exception.ServiceException;
import com.jnshu.sildenafil.system.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Taimur
 * @since 2018-11-11
 */
public interface ArticleService extends IService<Article> {
    /**前台分页查询card文章信息
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    IPage getFrontCardPageList(Integer page, Integer size);

    /**前台分页查询banner文章信息
     * @param page 页码
     * @param size 每页数量
     * @return 文章列表
     */
    List getFrontBannerPageList(Integer page, Integer size);

    /**前台根据文章id查询文章信息
     * @param articleId 文章id
     * @return 对应文章
     */
    Article getArticleById(Long articleId) throws ServiceException;
}
