package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Forum;
import com.jnshu.sildenafil.system.domain.Review;
import com.jnshu.sildenafil.system.mapper.ForumDao;
import com.jnshu.sildenafil.system.mapper.StudentDao;
import com.jnshu.sildenafil.system.service.ForumService;
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
public class ForumServiceImpl extends ServiceImpl<ForumDao, Forum> implements ForumService {
    @Autowired
    ForumDao forumDao;
    @Autowired
    StudentDao studentDao;
    @Override
    public IPage <Forum> forumFrontList(Integer page, Integer size){
        IPage<Forum> frontPage = new MyPage(page,size).setDesc("create_at");
        QueryWrapper<Forum> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_at");
        return forumDao.selectPage(frontPage,wrapper);
    }
    @Override
    public IPage<Forum> forumByStudent(Integer page, Integer size, Long studentId){
        page= null==page||page<=1 ? 1 : page;
        size= null==size||size<=1||size>20 ? 10 : size;
        IPage<Forum> iPage = new MyPage<>(page,size);
        QueryWrapper<Forum> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        wrapper.orderByDesc("create_at");
        return forumDao.selectPage(iPage,wrapper);
    }
}
