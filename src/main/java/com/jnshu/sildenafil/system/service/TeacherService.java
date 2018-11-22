package com.jnshu.sildenafil.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jnshu.sildenafil.common.exception.ParamIsNullException;
import com.jnshu.sildenafil.common.exception.ServiceException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihoo
 * @since 2018-10-31
 */
@SuppressWarnings("unused")
public interface TeacherService extends IService<Teacher> {

    /**
     * 后台查询老师昵称List
     * @return 老师昵称List
     */
    List getTeacherNameList();

    /**
     * 后台通过id查询老师详情
     * @param teacherId 老师id
     * @return 查询到的老师详情
     * @throws ParamIsNullException 空参
     */
    Teacher getTeacherById(Long teacherId) throws ParamIsNullException;

    /**
     * 根据视频List查询老师List
     * @param videoPage 视频page
     * @return 老师List
     * @throws ParamIsNullException 空餐
     */
    List<Teacher> getTeacherListByVideoList(IPage videoPage) throws ParamIsNullException;

}
