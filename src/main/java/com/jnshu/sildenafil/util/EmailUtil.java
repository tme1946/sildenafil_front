package com.jnshu.sildenafil.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: task6
 * @Package: jnshu.util
 * @ClassName: Email
 * @Description: 发送邮件的工具类
 * @Author: Taimur
 * @CreateDate: 2018/9/1 10:08
 */
@Component
public class EmailUtil {
    private static final String url = "http://api.sendcloud.net/apiv2/mail/send";
    private static final String apiUser = "Taimur_test_SNiWKh";
    private static final String apiKey = "wwZApmGFdgi6FjN5";
    private static final String subject = "Test2";
    private static final String from = "service@GCstudio.cn";
    private static final String fromName = "Taimur";
    /**
     * 发送邮件到address 
     * @param address（邮箱地址）
     * @return  void
     */
    public static void sendEmail(String address )throws IOException{
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String userName = "测试用户";
        String code = VerifyCode.codes(5);

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("to", address));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", "<img src=\"https://gcstudio-1251897722.cos.ap-chengdu.myqcloud.com/uugai.com_1542595691912.png\" style=\"border:0\"><p>亲爱的用户："+userName+"</p><p>这封邮件用于验证绑定邮箱</p><p>验证码：</p><h2>"+code+"</h2>"));
//        params.add(new BasicNameValuePair("html", "你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！"));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpClient.execute(httpPost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httpPost.releaseConnection();
    }

}
