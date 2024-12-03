package com.course.musicweb3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
* 热更新、热加载
* 1.compiler.automake.allow.when.app.running
* 2.CTRL+F9
* */
@SpringBootApplication
@MapperScan("com.course.musicweb3.dao")
public class MusicWeb3Application {

    public static void main(String[] args) {
        SpringApplication.run(MusicWeb3Application.class, args);
    }

}
