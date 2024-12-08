package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Singer;
import com.course.musicweb3.service.SingerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    /**
     * 该入参与视频不同，是请求体JSON入参，不是parametre注意一下
     * 上面那个写法太抽象了
     * @param singer
     * @return
     */
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
    @RequestMapping("/sex/detail")
    public Object singerOfSex(HttpServletRequest request){
        String sex=request.getParameter("sex");
        return singerService.singerOfSex(Integer.parseInt(sex));
    }
    @RequestMapping("/selectByPrimaryKey")
    public Object singerOfId(HttpServletRequest request){
        String id=request.getParameter("id");
        return singerService.selectByPrimaryKey(Integer.parseInt(id));
    }
    /**
     * 上传歌手图片
     */
    @RequestMapping(value = "/updateSingerPic",method = RequestMethod.POST)
    public Object uploadPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put("code",0);
            jsonObject.put("msg","文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"singerPic";
        //如果文件路径不存在新增路径
        File file1=new File(filePath);
        if(file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath="/img/singerPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Singer singer=singerService.selectByPrimaryKey(id);
            singer.setPic(storeAvatorPath);
            boolean flag=singerService.update(singer);
            if(flag){
                jsonObject.put("code",1);
                jsonObject.put("avator",storeAvatorPath);
                jsonObject.put("msg","上传成功");
                return jsonObject;
            }
            jsonObject.put("code",0);
            jsonObject.put("msg","上传失败");
        } catch (Exception e) {
            jsonObject.put("code",0);
            jsonObject.put("msg","上传失败"+e.getMessage());
        }
        return jsonObject;
    }
}
