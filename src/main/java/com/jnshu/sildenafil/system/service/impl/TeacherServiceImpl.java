package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jnshu.sildenafil.system.domain.Teacher;
import com.jnshu.sildenafil.system.domain.Video;
import com.jnshu.sildenafil.system.mapper.TeacherDao;
import com.jnshu.sildenafil.system.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jnshu.sildenafil.common.exception.ParamIsNullException;

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
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    /**
     * 后台查询老师昵称List
     * @return 老师昵称List
     */
    @Override
    public List getTeacherNameList() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        List<Teacher> teacherList = teacherDao.selectList(teacherQueryWrapper);
        List teacherNameList = teacherList.stream().map(Teacher::getNickname).collect(Collectors.toList());
        log.info("result of getTeacherNameList is: {}", teacherNameList);
        return teacherNameList;
    }

    /**
     * 后台通过id查询老师详情
     * @param teacherId 老师id
     * @return 查询到的老师详情
     */
    @Override
    public Teacher getTeacherById(Long teacherId) throws ParamIsNullException {
        log.info("args for getTeacherById is: {}", teacherId);
        if (teacherId != null) {
            Teacher teacher = teacherDao.selectById(teacherId);
            log.info("result of getTeacherById is: {}", teacher);
            return teacher;
        } else {
            log.error("result for getTeacherById error;teacherId is null");
            throw new ParamIsNullException("getTeacherById error;args null");
        }
    }

    /**
     * 根据视频List查询老师List
     * @param videoPage 视频page
     * @return 老师List
     * @throws ParamIsNullException 空餐
     */
    @Override
    public List<Teacher> getTeacherListByVideoList(IPage videoPage) throws ParamIsNullException {
        if (videoPage == null) {
            log.error("result for getTeacherListByVideoList error;videoId is null");
            throw new ParamIsNullException("getTeacherListByVideoList error;args null");
        }
        List<Video> videoList = videoPage.getRecords();
        List<Teacher> teacherList = videoList.stream().map(v -> teacherDao.selectById(v.getTeacherId())).collect(Collectors.toList());
        log.info("result for getTeacherListByVideoList size is:{}",teacherList.size());
        return teacherList;
    }

}
