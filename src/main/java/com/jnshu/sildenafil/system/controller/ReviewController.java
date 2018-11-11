package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Review;
import com.jnshu.sildenafil.system.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: sildenafil_front
 * @Package: com.jnshu.sildenafil.system.controller
 * @ClassName: ReviewController
 * @Description: java类作用描述
 * @Author: Taimur
 * @CreateDate: 2018/11/11 17:15
 */
@Slf4j
@Controller
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    /**
     *根据学生id获取评论列表（论坛）
     * @param [page, size, student_id]
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @ResponseBody
    @GetMapping(value = "/a/u/front/reviews/student")
    public ResponseBo listByStudent(Integer page,Integer size,Long studentId){
        IPage iPage = reviewService.reviewByStudent(page,size,studentId);
        return ResponseBo.ok().put("reviewList",iPage);
    }
    /**
     * 新建回复
     * @param [review]
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @ResponseBody
    @PostMapping(value = "/a/u/front/review")
    public ResponseBo addReview(Review review){
        Boolean isSaveReview = reviewService.save(review);
        return ResponseBo.ok().put("isSaveReview",isSaveReview);

    }

}
