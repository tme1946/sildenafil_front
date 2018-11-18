package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jnshu.sildenafil.system.domain.*;
import com.jnshu.sildenafil.system.mapper.CollectionAssetDao;
import com.jnshu.sildenafil.system.mapper.LikeAssetDao;
import com.jnshu.sildenafil.system.mapper.TeacherDao;
import com.jnshu.sildenafil.system.mapper.VideoDao;
import com.jnshu.sildenafil.system.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jnshu.sildenafil.util.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Taimur
 * @since 2018-10-31
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoDao, Video> implements VideoService {
//    private static final long NOW = System.currentTimeMillis();

    private final VideoDao videoDao;
    private final TeacherDao teacherDao;
    private final LikeAssetDao likeAssetDao;
    private final CollectionAssetDao collectionAssetDao;

    @Autowired(required = false)
    public VideoServiceImpl(VideoDao videoDao, TeacherDao teacherDao,
                            LikeAssetDao likeAssetDao, CollectionAssetDao collectionAssetDao) {
        this.videoDao = videoDao;
        this.teacherDao = teacherDao;
        this.likeAssetDao = likeAssetDao;
        this.collectionAssetDao = collectionAssetDao;
    }

    /**
     * 前台查询视频列表（card分页）
     * @param page 第几页
     * @param size 每页条数
     * @param grade 年级
     * @param subject 科目
     * @return 分页后的视频List
     */
    @Override
    public IPage getPage(Integer page, Integer size, Integer grade, Integer subject) {
        log.info("args for getPage is: {}", page, size, grade, subject);
        //调整page和size默认值--
        page= null==page||page<=1 ? 1 : page;
        size= null==size||size<=1||size>20 ? 10 : size;
        IPage<Video> findPage = new MyPage<Video>(page, size).setDesc("update_at");
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper
                .select("id","teacher_id","url","grade","subject","body","title","time_length",
                        "status","type","update_at","digest","like_amount","collection_amount")
                .eq("type", 0)
                .eq("status", 1)
                .eq(grade != null, "grade", grade)
                .eq(subject != null, "subject", subject);
        IPage videoIPage = videoDao.selectPage(findPage, videoQueryWrapper);
        if (videoIPage.getRecords().size() > 0) {
            log.info("Video size is: {}", videoIPage.getRecords().size());
            return videoIPage;
        } else {
            log.error("List is empty!");
            return null;
        }
    }

    /**
     * 前台id查询视频详情
     * @param videoId 视频id
     * @param studentId 学生id
     * @return 查询到的视频详情
     */
    @Override
    public Video getVideoById(Long videoId, Long studentId) {
        log.info("args for saveVideo is:videoId={}, studentId={}", videoId, studentId);
        if (videoId != null) {
            Video video = videoDao.selectById(videoId);
            log.info("result of getVideoById is: {}", video);
            return video;
        } else {
            log.error("args is null");
            return null;
        }
    }

    /**
     * 前台增加点赞数PUT
     * 一个学生id只能给一个视频点一个赞
     * @param videoId 视频id
     * @return 点赞数
     */
    @Override
    public int updateLikeAmount(Long videoId) {
        log.info("args for updateLikeAmount is: {}", videoId);
        QueryWrapper<LikeAsset> countQueryWrapper = new QueryWrapper<>();
        countQueryWrapper.eq("type_id", videoId);
        int likeAmount =  likeAssetDao.selectCount(countQueryWrapper);
        Video v = new Video();
        v.setId(videoId);
        v.setLikeAmount(likeAmount);
        Long id = videoDao.updateById(v) > 0 ? v.getId() : -10000;
        log.info("result for updateLikeAmount success; result detail: videoId={}", id);
        return likeAmount;
    }

    /**
     * 前台增加收藏数PUT
     * @param videoId 视频id
     * @return 收藏数
     */
    @Override
    public int updateCollectionAmount(Long videoId) {
        log.info("args for updateCollectionAmount is: {}", videoId);
        QueryWrapper<CollectionAsset> countQueryWrapper = new QueryWrapper<>();
        countQueryWrapper.eq("type_id", videoId);
        int collectionAmount =  collectionAssetDao.selectCount(countQueryWrapper);
        Video v = new Video();
        v.setId(videoId);
        v.setCollectionAmount(collectionAmount);
        Long id = videoDao.updateById(v) > 0 ? v.getId() : -10000;
        log.info("result for updateCollectionAmount success; result detail: videoId={}", id);
        return collectionAmount;
    }

    /**
     * 前台Banner视频列表
     * @return Banner视频List
     */
    @Override
    public IPage getBannerList(Integer page, Integer size) {
        log.info("args for getFrontPageList is : page={}&size={}",page,size);
        //调整page和size默认值--
        page= null==page||page<=1? 1 : page;
        size= null==size||size<=1||size>20 ? 10 : size;
        IPage<Video> getBannerPage=new MyPage<Video>(page,size).setDesc("update_at");
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper
                .select("id","type","cover","teacher_id","title","digest","url",
                        "body","like_amount","collection_amount","update_at","status")
                .eq("type", 1)
                .eq("status", 1);
        IPage<Video> videoBannerList = videoDao.selectPage(getBannerPage,videoQueryWrapper);
        if (videoBannerList.getRecords().size() > 0) {
            log.info("result for getBannerList success; videoList size is : ", videoBannerList.getRecords().size());
            return videoBannerList;
        } else {
            log.error("result for getBannerList error : list is null");
            return null;
        }
    }
}
