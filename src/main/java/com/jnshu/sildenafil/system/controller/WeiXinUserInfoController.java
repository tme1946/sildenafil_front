package com.jnshu.sildenafil.system.controller;

import com.jnshu.sildenafil.common.annotation.UseLog;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.domain.WeiXinUser;
import com.jnshu.sildenafil.system.domain.Student;
import com.jnshu.sildenafil.system.service.StudentService;
import com.jnshu.sildenafil.system.service.WeiXinUserInfoService;
import com.jnshu.sildenafil.util.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Map;

/**
 * @author lihoo
 * @create 2018-11-18
 * @desc 获取微信用户的所有信息，这个主要是为了不要用户自己填写个人信息
 **/
@Slf4j
@RestController
public class WeiXinUserInfoController {

    @Autowired
    private WeiXinUserInfoService weiXinService;
    @Autowired
    private StudentService studentService;

    /**
     * 进行网页授权，便于获取到用户的绑定的内容
     *
     * @param session
     * @param map
     * @return
     */

    @UseLog("用户登陆")
    @ResponseBody
    @RequestMapping("/a/a/student/{code}")
    public ResponseBo index(@PathVariable String code, HttpSession session, Map<String, Object> map) {
        //首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
        WeiXinUser weiXinUser = null;
        String openId = "";
        String accessToken = "";
            /**
             * code是获取openId必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
             * 会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
             */
        try {
            //得到当前用户的信息(具体信息就看weixinUser这个javabean)
            Map<String, String> authInfo = weiXinService.getAuthInfo(code);
            accessToken = authInfo.get("AccessToken");
            openId = authInfo.get("Openid");
            //将获取到的用户信息，放入到session中
            session.setAttribute("currentUser", weiXinUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Student student = studentService.login(openId);
        if (student == null) {
            student = register(accessToken, openId);
        }
        return ResponseBo.ok().put("data", student);
    }
    private Student register(String accessToken, String openId) {
        WeiXinUser userinfo = weiXinService.getUserInfo(accessToken, openId);
        Student student = new Student();
        String veriCode = VerifyCode.numbers(4);
        String nickname = userinfo.getNickname() + "#" + veriCode;
        String img = userinfo.getHeadImgUrl();
        student.setNickname(nickname);
        student.setImg(img);
        student.setCreateAt(System.currentTimeMillis());
        student.setOpenid(openId);
        if (studentService.save(student)) {
            return studentService.login(openId);
        } else {
            log.error("result for createStudent error");
            return student = null;
        }
    }
}
