package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Singer;
import com.course.musicweb3.service.SingerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/singer")
public class SingerController {
    SingerService singerService;
    @Autowired
    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }
    @RequestMapping("/add")
    public Object addsinger(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name").trim();
        String pic=request.getParameter("pic").trim();
        String sex=request.getParameter("sex").trim();
        String birth=request.getParameter("birth").trim();
        String location=request.getParameter("location").trim();
        String introduction=request.getParameter("introduction").trim();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate=new Date();
        try{
            birthDate=dateFormat.parse(birth);
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存到歌手的对象中
        Singer singer=new Singer();
        singer.setName(name);
        singer.setPic(pic);
        singer.setSex(Byte.parseByte(sex));
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.insert(singer);
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","添加成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","添加失败");
        }
        return jsonObject;
    }
    @RequestMapping("/delete")
    public Object deleteSinger(HttpServletRequest request){
        String id=request.getParameter("id");
        boolean flag=singerService.delete(Integer.parseInt(id));
        JSONObject jsonObject=new JSONObject();
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","删除成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","删除失败");
        }
        return jsonObject;
    }
    @RequestMapping("/update")
    public Object updateSinger(@RequestBody Singer singer){
        return singerService.update(singer);
    }
    @RequestMapping("/allSinger")
    public Object allSinger(){
        return singerService.allSinger();
    }
    @RequestMapping("/singerOfName")
    public Object singerOfName(HttpServletRequest request){
        String name=request.getParameter("name");
        return singerService.singerOfName(name);
    }
    @RequestMapping("/singerOfSex")
    public Object singerOfSex(HttpServletRequest request){
        String sex=request.getParameter("sex");
        return singerService.singerOfSex(Integer.parseInt(sex));
    }
    @RequestMapping("/singerOfId")
    public Object singerOfId(HttpServletRequest request){
        String id=request.getParameter("id");
        return singerService.selectByPrimaryKey(Integer.parseInt(id));
    }
}
