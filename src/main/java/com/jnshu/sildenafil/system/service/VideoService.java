package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihoo
 * @since 2018-11-03
 */
@SuppressWarnings("unused")
public interface VideoService extends IService<Video> {

    /**
     * 前台查询视频列表（分页）
     * @param page 第几页
     * @param size 每页条数
     * @param grade 年级
     * @param subject 科目
     * @return 分页后的视频List
     */
    IPage getPage(Integer page, Integer size, Integer grade, Integer subject);

    /**
     * 前台查询视频详情
     * @param videoId 视频id
     * @return 查询到的视频详情
     */
    Video getVideoById(Long videoId);

    /**
     * 前台增加点赞数
     * @param videoId 视频id
     * @return 视频点赞数
     */
    int updateLikeAmount(Long videoId);

    /**
     * 前台增加收藏数
     * @param videoId 视频id
     * @return 视频收藏数
     */
    int updateCollectionAmount(Long videoId);

    /**
     * 前台Banner视频列表
     * @return Banner视频List
     */
    List getBannerList();

}
