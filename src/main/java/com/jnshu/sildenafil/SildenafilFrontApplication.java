package com.jnshu.sildenafil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.jnshu.sildenafil.system.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class SildenafilFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(SildenafilFrontApplication.class, args);
        System.out.println("***** spring boot sildenafil front start success *****");
    }
}
