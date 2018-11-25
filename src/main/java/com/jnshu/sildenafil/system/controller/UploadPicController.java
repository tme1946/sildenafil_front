package com.jnshu.sildenafil.system.controller;

import com.aliyun.oss.OSSClient;
import com.jnshu.sildenafil.common.domain.ResponseBo;
import com.jnshu.sildenafil.util.AliyunOSSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

import static com.jnshu.sildenafil.util.OSSClientConstants.BUCKET_NAME;
import static com.jnshu.sildenafil.util.OSSClientConstants.FOLDER;

/**
 * #Title: UploadPicController
 * #ProjectName sildenafil_front
 * #Description: 图片上传
 * #author lihoo
 * #date 2018/11/25-11:22
 * @author lihoo
 */

@Controller
@Slf4j
@SuppressWarnings("unchecked")
public class UploadPicController {

    @ResponseBody
    @PostMapping(value = "/a/image/upload")
    public ResponseBo uploadPic(String img) {
        if (img==null) {
            log.error("args for uploadPic is null.");
            return ResponseBo.error("参数为空，请检查入参");
        }
        log.info("args for uploadPic : img={}",img);
        OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();
        File uploadFile=new File(img);
        log.info("查看上传图片格式信息："+ uploadFile);
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, uploadFile, BUCKET_NAME, FOLDER);
        log.info("上传后的文件MD5数字唯一签名:" + md5key);
        if (md5key != null) {
            return ResponseBo.ok("接口通，成功获取数据").put("data",md5key);
        } else {
            log.error("result for uploadPic is null");
            return ResponseBo.error("结果异常");
        }
    }
}
