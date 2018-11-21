package com.jnshu.sildenafil.system.controller;

import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.system.bean.WeiXinUser;
import com.jnshu.sildenafil.system.service.WeiXinUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
    private WeiXinUserInfoService userService;

    /**
     * 进行网页授权，便于获取到用户的绑定的内容
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("/a/a/student/{code}")
    public ResponseBo index(@PathVariable String code , HttpSession session, Map<String, Object> map) {
        //首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
        WeiXinUser  weiXinUser = null;
        if(session.getAttribute("currentUser") != null){
            weiXinUser = (WeiXinUser) session.getAttribute("currentUser");
        }else {
            /**
             * code是获取openId必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
             * 会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
             */
            log.info("微信*aa*--code:"+ code);
            try {
                //得到当前用户的信息(具体信息就看weixinUser这个javabean)
                weiXinUser = getTheCode(code);
                log.info("微信user:"+weiXinUser);
                //将获取到的用户信息，放入到session中
                session.setAttribute("currentUser", weiXinUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("weiXinUser", weiXinUser);
        return ResponseBo.ok("得到code");
    }
    /**
     * 获取用户的openId
     * @param code
     * @return 返回封装的微信用户的对象
     */
    private WeiXinUser getTheCode(String code) {
        Map<String , String>  authInfo = new HashMap<>();
        String openId = "";
        String accessToken = "";
        if (code != null) {
            // 调用根据用户的code得到需要的授权信息
            authInfo= userService.getAuthInfo(code);
            log.info("授权信息:"+authInfo);
            //获取到accessToken
            accessToken = authInfo.get("AccessToken");
            log.info("授权accessToken:"+accessToken);
            //获取到openId
            openId = authInfo.get("Openid");
            log.info("授权openid:"+openId);
        }
         /*
         获取基础刷新的接口访问凭证
         （为什么不用authInfo.get("AccessToken");
         因为这里面的access_token和基础的那个access_token不一样，
         这里获取的是授权的token）
         */
//        String accessToken = WeiXinUtils.getAccessToken().getToken();
        //String accessToken = authInfo.get("AccessToken");
        //获取到微信用户的信息
        WeiXinUser userinfo = userService.getUserInfo(accessToken ,openId);
        log.info("用户信息ctrl--openid:"+userinfo.getOpenId());
        log.info("用户信息ctrl--nickname:"+userinfo.getNickname());
        log.info("用户信息ctrl--headimg:"+userinfo.getHeadImgUrl());
        return userinfo;
    }
}
