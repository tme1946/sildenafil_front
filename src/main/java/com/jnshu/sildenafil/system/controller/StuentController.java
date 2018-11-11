package com.jnshu.sildenafil.system.controller;

import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.Student;
import com.jnshu.sildenafil.system.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: sildenafil_front
 * @Package: com.jnshu.sildenafil.system.controller
 * @ClassName: StuentController
 * @Description: java类作用描述
 * @Author: Taimur
 * @CreateDate: 2018/11/11 20:16
 */
@Slf4j
@Controller
public class StuentController {
    @Autowired
    StudentService studentService;
    private Long TIME = System.currentTimeMillis();
    /**
     * 验证登陆方法（未使用安全框架设计）            
     * @param [openId]  
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @ResponseBody
    @GetMapping(value = "/a/verify")
    public ResponseBo login(String openId){
        if(openId == null){
            log.error("args for openId is null");
            return ResponseBo.error("openId is null"); }
        log.info("args for loginByOpenId is : openId={}",openId);
        Student student = studentService.login(openId);
        if(student.getId() != null){
            return ResponseBo.error("student not exist");
        }
        log.info("result for loginByStudentId is : studentId={}",student.getId());
        return ResponseBo.ok().put("student",student);
    }
    /**
     * 修改学生信息
     * @param [student]
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @ResponseBody
    @PutMapping(value = "/a/u/front/student")
    public ResponseBo updateStudent(Student student){
        if(student == null){
            log.error("args for student is null");
            return ResponseBo.error("student is null");
        }
        log.info("args for updateStudentId is : studentId={}",student.getId());
        student.setUpdateAt(TIME);
        if(studentService.updateById(student)){
            log.info("result for updateStudentId is : studentId={}",student.getId());
            return ResponseBo.ok().put("data",student);
        }else{
            log.error("result for updateStudent error");
            return ResponseBo.error("student update error");
        }
    }
    /**
     * 查询学生信息（主键查询） 
     * @param [id]
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @ResponseBody
    @GetMapping(value = "/a/u/front/student")
    public ResponseBo getStudent(Long id){
        if(id == null){
            log.error("args for studentId is null");
            return ResponseBo.error("studentId is null");
        }
        log.info("args for getStudentId is : studentId={}",id);
        Student student = studentService.getById(id);
        if(student != null){
            log.info("result for getStudentById is : studentId={}",id);
            return ResponseBo.ok().put("data",student);
        }else{
            log.error("result for getStudent is student not exist");
            return ResponseBo.error("student not exist");
        }
    }

}
