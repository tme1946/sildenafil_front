package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.exception.ParamIsNullException;
import com.jnshu.sildenafil.system.domain.Video;
import com.baomidou.mybatisplus.extension.service.IService;


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
     * 前台查询视频列表（card分页）
     * @param page 第几页
     * @param size 每页条数
     * @param grade 年级
     * @param subject 科目
     * @return 分页后的视频List
     * @throws ParamIsNullException 参数为空
     */
    IPage getPage(Integer page, Integer size, Integer grade, Integer subject) throws ParamIsNullException;

    /**
     * 前台查询视频详情
     * @param videoId 视频id
     * @param studentId 学生id
     * @return 查询到的视频详情
     * @throws ParamIsNullException 参数为空
     */
    Video getVideoById(Long videoId, Long studentId) throws ParamIsNullException;

    /**
     * 前台增加点赞数
     * @param videoId 视频id
     * @return 视频点赞数
     * @throws ParamIsNullException 参数为空
     */
    int updateLikeAmount(Long videoId) throws ParamIsNullException;

    /**
     * 前台增加收藏数
     * @param videoId 视频id
     * @return 视频收藏数
     * @throws ParamIsNullException 参数为空
     */
    int updateCollectionAmount(Long videoId) throws ParamIsNullException;

    /**
     * 前台Banner视频列表
     * @param page 第几页
     * @param size 每页条数
     * @return Banner视频List
     * @throws ParamIsNullException 参数为空
     */
    IPage getBannerList(Integer page, Integer size) throws ParamIsNullException;
}
