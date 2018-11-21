package com.jnshu.sildenafil.util;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @ProjectName: task6
 * @Package: jnshu.util
 * @ClassName: ShortMassage
 * @Description: 短信工具类
 * @Author: Taimur
 * @CreateDate: 2018/9/2 17:17
 */
@Component
public class ShortMassage {
    private static final int appid = 1400136173;
    private static final String appkey = "f7343bd28da2ea80b54cf926e03b0a6c";
    private static final int verifyTemplateId = 231571;
    private static final int multiTemplateId = 186352;
    private static final String smsSign = "sildenafil";
    /**
     * 发送短信（单个） 
     * [phoneNumbers]
     * @return  java.lang.String
     */
    public static String singleSend(String phoneNumbers,String code){
        SmsSingleSenderResult result = new SmsSingleSenderResult();
        try {
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
//            ArrayList<String> params =VerifyCode.numbers(6,1);
//            code = params.get(0);
            ArrayList<String> params = new ArrayList<>();
            params.add(code);
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
             result = ssender.sendWithParam(
                            "86", phoneNumbers, verifyTemplateId, params, smsSign, "", "");
            System.out.println(result);

        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * 发送短信（群发） 
     * [phoneNumbers]
     * @return  java.util.List
     */
    public static void multiSend(ArrayList<String> phoneNumbers){
        try {
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            ArrayList<String> params = new ArrayList<>();
            params.add("老铁最帅");
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers, multiTemplateId, params, smsSign, "", "");
//            SmsMultiSenderResult result =  msender.send(0, "86", phoneNumbers,
//                    "【腾讯云】这是一条群发短信测试", "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }

}
