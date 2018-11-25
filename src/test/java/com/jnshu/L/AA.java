package com.jnshu.L;

import java.util.HashSet;
import java.util.Set;

/**
 * #Title: AA
 * #ProjectName sildenafil_front
 * #Description: TODO
 * #author lihoo
 * #date 2018/11/23-20:47
 */


public class AA {

//    public static void main(String[] args) {
////        int i, sum=0;
////        for (i=0;i<10;++i,sum+=i) {
////            System.out.println(i);
////        }
//        Integer a = 10;
//        System.out.println(a);
//    }

    public static void main(String[] args) {

        Set set = new HashSet();

        for (int i = 0; i < 10; i++) {
            Object object = new Object();
            set.add(object);

            // 设置为空，这对象我不再用了
            object = null;
        }

        // 但是set集合中还维护这obj的引用，gc不会回收object对象
        set.forEach(System.out::println);
    }

}
