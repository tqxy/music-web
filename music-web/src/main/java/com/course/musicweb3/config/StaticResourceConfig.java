package com.course.musicweb3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位歌手头像地址
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("//img/singerPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator"));
        //歌曲图片地址
        registry.addResourceHandler("//img/songPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator"));
        //歌单图片地址
        registry.addResourceHandler("//img/songListPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator"));
        //歌曲图片地址
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator"));
        //歌曲图片地址
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator"));
        //歌单图片地址
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"img" +
                        System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator"));
        //歌曲地址
        registry.addResourceHandler("/song/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"song" +
                        System.getProperty("file.separator"));
        //用户头像
        registry.addResourceHandler("//avatorImages/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"avatorImages"
                        +System.getProperty("file.separator"));
        //用户头像
        registry.addResourceHandler("/avatorImages/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+ System.getProperty("file.separator")+"avatorImages"
                        +System.getProperty("file.separator"));
    }
}
