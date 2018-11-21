package com.jnshu.sildenafil.system.controller;

import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.bean.WeiXinUser;
import com.jnshu.sildenafil.system.domain.Article;
import com.jnshu.sildenafil.system.domain.FrontLog;
import com.jnshu.sildenafil.system.domain.Student;
import com.jnshu.sildenafil.system.service.ArticleService;
import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.jnshu.sildenafil.system.service.StudentService;
import com.jnshu.sildenafil.system.service.WeiXinUserInfoService;
import com.jnshu.sildenafil.util.EmailUtil;
import com.jnshu.sildenafil.util.RedisUtil;
import com.jnshu.sildenafil.util.ShortMassage;
import com.jnshu.sildenafil.util.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private StudentService studentService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CollectionAssetService collectionAssetService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WeiXinUserInfoService  weiXinService;
    /**
     * 验证登陆方法（未使用安全框架设计）            
     * @param code
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("用户登陆")
    @ResponseBody
    @GetMapping(value = "/a/verify")
    public ResponseBo login(String code){
        if(code == null){
            log.error("args for openId is null");
            return ResponseBo.error("openId is null"); }
        Map getOpenId = weiXinService.oauth2GetOpenid(code);
        String openId = (String) getOpenId.get("Openid");
        Student student = studentService.login(openId);
        if(student == null){
            String accessToken = (String)getOpenId.get("AccessToken");
            WeiXinUser weiXinUser = weiXinService.getUserInfo(accessToken,openId);
            String veriCode = VerifyCode.numbers(4);
            String nickname = weiXinUser.getNickname()+"#"+veriCode;
            String img = weiXinUser.getHeadImgUrl();
            student.setNickname(nickname);
            student.setImg(img);
            student.setCreateAt(System.currentTimeMillis());
            student.setOpenid(openId);
            if(studentService.save(student)) {
                return ResponseBo.ok().put("data", studentService.login(openId));
            }else {
                log.error("result for createStudent error");
                return ResponseBo.error("student create error");
            }
        }else {
            return ResponseBo.ok().put("data", student);
        }
    }
    /**
     * 修改学生信息
     * @param student
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("修改学生信息")
    @ResponseBody
    @PutMapping(value = "/a/u/front/student")
    public ResponseBo updateStudent(Student student){
        if(student == null){
            log.error("args for student is null");
            return ResponseBo.error("student is null");
        }
        student.setUpdateAt(System.currentTimeMillis());
        if(studentService.updateById(student)){
            return ResponseBo.ok().put("data",student);
        }else{
            log.error("result for updateStudent error");
            return ResponseBo.error("student update error");
        }
    }
    /**
     * 查询学生信息（主键查询） 
     * @param studentId
     * @return  com.jnshu.sildenafil.common.domain.ResponseBo
     */
    @UseLog("查询学生信息")
    @ResponseBody
    @GetMapping(value = "/a/u/front/student")
    public ResponseBo getStudent(Long studentId){
        if(studentId == null){
            log.error("args for studentId is null");
            return ResponseBo.error("studentId is null");
        }
        Student student = studentService.getById(studentId);
        if(student != null){
            return ResponseBo.ok().put("data",student);
        }else{
            log.error("result for getStudent is student not exist");
            return ResponseBo.error("student not exist");
        }
    }
    /**
     * 发送验证码(type:0,手机/1,邮箱）
     * @param account
     * @param type
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/a/u/front/code")
    public ResponseBo sendCode(String account,Integer type,Long studentId) throws Exception{
        String result = null;
        String code = null;
        Boolean allowSend;
        String key = studentId+"-"+type;
        if(redisUtil.get(key) == null){
            redisUtil.set(key,0,86400);
        }
        Integer value = (Integer)redisUtil.get(key);
        if(value < 5){
            allowSend = true;
            value++;
            switch (type){
                case 0:
                    code = VerifyCode.numbers(5);
                    result = ShortMassage.singleSend(account,code);
                    break;
                case 1:
                    code = VerifyCode.codes(5);
                    result = EmailUtil.sendEmail(account,code);
                    break;
                default:
            }
            System.out.println("account："+account+"code："+code);
            redisUtil.set(account,code,600);
            redisUtil.set(key,value,86400);
        }else{
            allowSend = false;
        }
        System.out.println("value:"+value);
        return ResponseBo.ok().put("data",allowSend).put("result",result);
    }
    @UseLog("绑定0手机/1邮箱")
    @ResponseBody
    @GetMapping(value = "/a/u/front/bind")
    public ResponseBo bind(Long studentId,String account,Integer type,String code){
        if(studentId == null){
            log.error("args for studentId is null");
            return ResponseBo.error("studentId is null");}
        String redisCode = (String)redisUtil.get(account);
        if(code.equals(redisCode)){
            Student student = studentService.getById(studentId);
            if(type == 0){
                student.setPhone(account);
            }else if(type == 1){
                student.setEmail(account);
            }
            student.setUpdateAt(System.currentTimeMillis());
            student.setUpdateBy(student.getNickname());
            Integer bean = student.getBean();
            bean += 20;
            student.setBean(bean);
            studentService.updateById(student);
        }
        return ResponseBo.ok();
    }

}
