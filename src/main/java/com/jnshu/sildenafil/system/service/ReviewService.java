package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Review;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Taimur
 * @since 2018-11-11
 */
public interface ReviewService extends IService<Review> {
    IPage<Review> reviewByStudent(Integer page, Integer size, Long studentId);
    IPage reviewByType(Integer page,Integer size,Integer type,Long typeId);
}
