package com.jnshu.sildenafil.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Forum;
import com.jnshu.sildenafil.system.domain.Student;
import com.jnshu.sildenafil.system.service.ForumService;
import com.jnshu.sildenafil.system.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: sildenafil
 * @Package: com.jnshu.sildenafil.system.controller
 * @ClassName: ForumController
 * @Description: 帖子模块Controller
 * @Author: Taimur
 * @CreateDate: 2018/11/3 14:11
 */
@Slf4j
@Controller
public class ForumController {
    @Autowired
    ForumService forumService;
    @Autowired
    StudentService studentService;

    /**
     *  新建帖子
     * @param forum
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("新建帖子")
    @ResponseBody
    @PostMapping(value = "/a/u/front/forum")
    public ResponseBo addForum(Forum forum){
        forum.setCreateAt(System.currentTimeMillis());
        Boolean isSave = forumService.save(forum);
        if(isSave) {
            return ResponseBo.ok().put("data", isSave);
        }else {
            log.error("createForumError");
            return ResponseBo.error("新建帖子失败");
        }
    }

    /**帖子主键查询
     * @param forumId
     * @return
     */
    @UseLog("查询帖子")
    @ResponseBody
    @GetMapping(value = "/a/u/front/forum")
    public ResponseBo getForum(Long forumId){
        Forum forum = forumService.getById(forumId);
        return ResponseBo.ok().put("data",forum);
    }
    /**删除帖子
     * @param forumId
     * @return
     */
    @UseLog("删除帖子")
    @ResponseBody
    @DeleteMapping(value = "/a/u/front/forum")
    public ResponseBo delForum(Long forumId){
        Boolean isDel = forumService.removeById(forumId);
        if(isDel) {
            return ResponseBo.ok().put("data", isDel);
        }else{
            log.error("deleteForumError");
            return ResponseBo.error("删除失败");
        }
    }
    /**
     * 前台帖子列表（附带输出学生昵称，头像）
     * @param page, size
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("帖子列表")
    @ResponseBody
    @GetMapping(value = "/a/u/front/forum/list")
   public ResponseBo frontForumList(Integer page, Integer size){
        IPage<Forum> forumIPage = forumService.forumFrontList(page,size);
        List<Forum> forumList = forumIPage.getRecords();
        List<Long> idList = forumList.stream().map(Forum::getStudentId).collect(Collectors.toList());
        List<Student> studentList = idList.stream().map((id)->studentService.getById(id)).collect(Collectors.toList());
        List imgList = studentList.stream().map(Student::getImg).collect(Collectors.toList());
        List nicknameList = studentList.stream().map(Student::getNickname).collect(Collectors.toList());
        return ResponseBo.ok().put("forumList",forumIPage).put("nicknameList",nicknameList).put("imgList",imgList);
    }
    /**
     * 学生发帖列表（学生证/我的帖子） 
     * @param page, size, studentId
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("学生发帖列表")
    @ResponseBody
    @GetMapping(value ="/a/u/front/forum/student")
    public ResponseBo forumByStudent(Integer page, Integer size, Long studentId){
        IPage<Forum> forumByStudent = forumService.forumByStudent(page,size,studentId);
        return ResponseBo.ok().put("data",forumByStudent);
    }
}
