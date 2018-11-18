package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private VideoService videoService;

    @ResponseBody
    @GetMapping(value = "/a/u/front/video/")
    public ResponseBo getVideoPage(Integer page, Integer size, Integer grade, Integer subject) {
        if(page == null || size == null) {
            log.error("args is null");
            return ResponseBo.error("page or size is null");
        }
        log.info("args for getVideoPage is : page={}, size={}",page,size);
        IPage iPage = videoService.getPage(1,5,0,0);
        if(iPage != null){
            log.info("result for getVideoPage is : iPage={}",iPage);
            return ResponseBo.ok().put("data",iPage);
        }else{
            log.error("result for getVideoPage is not exist");
            return ResponseBo.error("iPage not exist");
        }


    }
}
