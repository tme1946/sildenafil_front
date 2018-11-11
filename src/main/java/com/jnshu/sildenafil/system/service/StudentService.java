package com.jnshu.sildenafil.system.service;

import com.jnshu.sildenafil.system.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Taimur
 * @since 2018-11-11
 */
public interface StudentService extends IService<Student> {
    /**
     * 用户查询（通过openId）
     * @param [openId]
     * @return  com.jnshu.sildenafil.system.domain.Student
     */
    Student login (String openId);
}
