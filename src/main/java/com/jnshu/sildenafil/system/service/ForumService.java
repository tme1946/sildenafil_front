package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Forum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jnshu.sildenafil.system.domain.Review;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Taimur
 * @since 2018-11-11
 */
public interface ForumService extends IService<Forum> {
    IPage<Forum> forumByStudent(Integer page, Integer size, Long studentId);
    IPage forumFrontList(Integer page, Integer size);
}
