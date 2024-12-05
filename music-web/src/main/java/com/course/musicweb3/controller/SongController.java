package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Song;
import com.course.musicweb3.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongService songService;
    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }
    @RequestMapping("/add")
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile) {
        //获取参数
        JSONObject jsonObject=new JSONObject();
        String singerId=request.getParameter("singerId").trim();
        String name=request.getParameter("name").trim();
        String introduction=request.getParameter("introduction").trim();
        String pic="/img/songPic/hhh.png";//默认图片
        String lyric=request.getParameter("lyric").trim();

        //歌曲文件处理
        if(mpFile.isEmpty()){
            jsonObject.put("code",0);
            jsonObject.put("msg","歌曲上传失败");
            return jsonObject;
        }
        String fileName=System.currentTimeMillis()+mpFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        File file1=new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        String storeUrlPath="/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song=new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag=songService.insert(song);
            if(flag){
                jsonObject.put("code",1);
                jsonObject.put("msg","上传成功");
                jsonObject.put("musicUrl",storeUrlPath);
                return jsonObject;
            }
            jsonObject.put("code",0);
            jsonObject.put("msg","上传失败");
            return jsonObject;
        }catch (Exception e){
            jsonObject.put("code",0);
             jsonObject.put("msg","上传失败"+e.getMessage());
             return jsonObject;
        }

    }
    @RequestMapping(value="/singer/detail")
    public Object objectOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }
    @RequestMapping(value = "/update")
    public Object updateSong(@RequestBody Song song) {
        System.out.println(song);
        return songService.update(song);
    }
    @RequestMapping(value="/delete")
    public Object deleteSong(HttpServletRequest request){
        String id=request.getParameter("id");
        return songService.delete(Integer.parseInt(id));
    }
    /**
     * 上传歌曲图片
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put("code",0);
            jsonObject.put("msg","文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //如果文件路径不存在新增路径
        File file1=new File(filePath);
        if(file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath="/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song=songService.selectByPrimaryKey(id);
            song.setPic(storeAvatorPath);
            boolean flag=songService.update(song);
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
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put("code",0);
            jsonObject.put("msg","文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //如果文件路径不存在新增路径
        File file1=new File(filePath);
        if(file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath="/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song=songService.selectByPrimaryKey(id);
            song.setUrl(storeAvatorPath);
            boolean flag=songService.update(song);
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
