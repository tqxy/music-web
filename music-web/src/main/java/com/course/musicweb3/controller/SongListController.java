package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.SongList;
import com.course.musicweb3.service.SongListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/songList")
public class SongListController {
    private final SongListService songListService;

    @Autowired
    public SongListController(SongListService songListService) {
        this.songListService = songListService;
    }

    @RequestMapping("/add")
    public Object addSongList(HttpServletRequest request) {
        //获取参数
        String title=request.getParameter("title").trim();
        String pic=request.getParameter("pic").trim();
        String introduction=request.getParameter("introduction").trim();
        String style=request.getParameter("style").trim();

        //保存到歌单对象
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        return songListService.insert(songList);
    }
    @RequestMapping(value="/allSongList")
    public Object allSongList(){
        return songListService.allSongList();
    }
    @RequestMapping(value = "/update")
    public Object updateSong(@RequestBody SongList songList) {
        return songListService.update(songList);
    }
    @RequestMapping(value="/delete")
    public Object deleteSong(HttpServletRequest request){
        String id=request.getParameter("id");
        return songListService.delete(Integer.parseInt(id));
    }
    @RequestMapping(value="/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id=request.getParameter("id");
        return songListService.selectByPrimaryKey(Integer.parseInt(id));
    }
    @RequestMapping(value="/title")
    public Object songListOfTitle(HttpServletRequest request){
        String title=request.getParameter("title");
        return songListService.songListOfTitle(title);
    }
    @RequestMapping(value = "/likeTitle")
    public Object likeTitle(HttpServletRequest request){
        String title=request.getParameter("title");
        return songListService.likeTitle(title);
    }
    @RequestMapping(value = "/likeStyle")
    public Object likeStyle(HttpServletRequest request){
        String style=request.getParameter("style");
        return songListService.likeStyle(style);
    }
    /**
     * 上传歌曲图片
     */
    @RequestMapping(value = "/updateSongListPic",method = RequestMethod.POST)
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
                +System.getProperty("file.separator")+"songListPic";
        //如果文件路径不存在新增路径
        File file1=new File(filePath);
        if(file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath="/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList=songListService.selectByPrimaryKey(id);
            songList.setPic(storeAvatorPath);
            boolean flag=songListService.update(songList);
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
    @RequestMapping("style/detail")
    public Object songListOfStyle(HttpServletRequest request){
        String style=request.getParameter("style");
        return songListService.likeStyle(style);
    }
}
