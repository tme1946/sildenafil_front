package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Article;
import com.jnshu.sildenafil.system.domain.Video;
import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.jnshu.sildenafil.system.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * #Title: VideoController
 * #ProjectName sildenafil_front
 * #Description: TODO
 * #author lihoo
 * #date 2018/11/12-20:33
 * @author lihoo
 */

@Controller
@Slf4j
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }
    @Autowired
    private CollectionAssetService collectionAssetService;

    @ResponseBody
    @GetMapping(value = "/a/u/front/video/list")
    public ResponseBo getCardVideoPage(Integer page, Integer size, Integer grade, Integer subject) {
        if(page == null || size == null) {
            log.error("args is null");
            return ResponseBo.error("page or size is null");
        }
        log.info("args for getCardVideoPage is : page={}, size={}",page,size);
        IPage iPage = videoService.getPage(page,size,grade,subject);
        if(iPage != null){
            log.info("result for getCardVideoPage is : iPage={}",iPage);
            return ResponseBo.ok().put("data",iPage);
        }else{
            log.error("result for getCardVideoPage is null");
            return ResponseBo.error("iPage is null");
        }
    }

    @ResponseBody
    @GetMapping(value = "/a/u/front/video/banner")
    public ResponseBo getBannerVideoPage(Integer page, Integer size) {

        if(page == null || size == null) {
            log.error("args is null");
            return ResponseBo.error("page or size is null");
        }
        log.info("args for getBannerVideoPage is : page={}, size={}",page,size);
        IPage iPage = videoService.getBannerList(page,size);
        if(iPage != null){
            log.info("result for getBannerVideoPage is : iPage={}",iPage);
            return ResponseBo.ok().put("data",iPage);
        }else{
            log.error("result for getBannerVideoPage is null");
            return ResponseBo.error("iPage is null");
        }

    }

    @ResponseBody
    @GetMapping(value = "/a/u/front/video")
    public ResponseBo getVideoById(Long videoId, Long studentId) {
        log.info("args for getVideoById is : videoId={}", videoId, studentId);
        Video video = videoService.getVideoById(videoId, studentId);
        if (video == null) {
            return ResponseBo.error("参数异常，获取数据失败");
        }
        return ResponseBo.ok("接口通，成功获取数据").put("data",video);
    }
    /**
     * 学生视频收藏列表 
     * @param studentId
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("学生视频收藏列表")
    @ResponseBody
    @GetMapping(value = "/a/u/front/video/collection/student")
    public ResponseBo collectionByStudent(Long studentId) throws Exception{
        if(studentId == null){
            log.error("args for studentId is null");
            return ResponseBo.error("studentId is null");}
        List<Long> typeIdList =collectionAssetService.collectiongListByStudent(1,studentId);
        List<Video> typeList = typeIdList.stream().map(id ->videoService.getById(id)).collect(Collectors.toList());
        return ResponseBo.ok().put("data", typeList);
    }


}
