package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Review;
import com.jnshu.sildenafil.system.mapper.ReviewDao;
import com.jnshu.sildenafil.system.service.ReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jnshu.sildenafil.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Taimur
 * @since 2018-11-11
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewDao, Review> implements ReviewService {
    @Autowired
    ReviewDao reviewDao;

    @Override
    public IPage<Review> reviewByStudent(Integer page, Integer size, Long studentId){
        IPage<Review> iPage = new MyPage<>();
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1);
        wrapper.eq("student_id",studentId);
        wrapper.orderByDesc("create_at");
        return reviewDao.selectPage(iPage,wrapper);
    }
}
