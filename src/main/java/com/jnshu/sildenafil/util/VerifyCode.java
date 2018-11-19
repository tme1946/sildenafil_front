package com.jnshu.sildenafil.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * @ProjectName: task6
 * @Package: jnshu.util
 * @ClassName: VerifyCode
 * @Description: 验证码工具类
 * @Author: Taimur
 * @CreateDate: 2018/9/2 22:03
 */
public  class VerifyCode {
    private static final char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
//            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
//            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    /**
     * 批量生成验证码（数字） digitint是每个单位显示的位数
     * @param [digitint, number]
     * @return  java.util.ArrayList<java.lang.String>
     */
    public static ArrayList<String> numbers(int digitint,int number){
        ArrayList<String> numbers = new ArrayList<>();
        for(int n = 0;n < number;n++) {
            String str = numbers(digitint);
            numbers.add(str);
        }
        return numbers;
    }
    /**
     * 生成一组验证码 （数字） 
     * @param [digitint]
     * @return  java.lang.String
     */
    public static String numbers(int digitint){
        String str = "";
        Random random = new Random();
        for (int i = 0; i < digitint; i++) {
            char num = ch[random.nextInt(10)];
            str += num;
        }
        return str;
    }
    /**
     * 批量生成验证码 (数字+字母)
     * @param [digitint, number]
     * @return  java.util.ArrayList<java.lang.String>
     */
    public static ArrayList<String> codes(int digitint,int number){
        ArrayList<String> codes = new ArrayList<>();
        for(int n = 0;n < number;n++) {
            String str = codes(digitint);
            codes.add(str);
        }
        return codes;
    }
    /**
     *  生成一组验证码
     * @param [digitint]
     * @return  java.lang.String
     */
    public static String codes(int digitint){
        String str = "";
        Random random = new Random();
        for (int i = 0; i < digitint; i++) {
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }
}
