package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.ListSong;
import com.course.musicweb3.service.ListSongService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listSong")
public class ListSongController {
    private final ListSongService listSongService;
    @Autowired
    public ListSongController(ListSongService listSongService){
        this.listSongService=listSongService;
    }
    //给歌单添加歌曲
    @RequestMapping("/add")
    public Object addListSong(HttpServletRequest request) {
        //获取参数
        JSONObject jsonObject=new JSONObject();
        String songId=request.getParameter("songId").trim();
        String songListId=request.getParameter("songListId").trim();

        ListSong listSong=new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));

        return listSongService.insert(listSong);
    }
    @RequestMapping(value="/songList/detail")
    public Object objectOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return listSongService.listSongOfListId(Integer.parseInt(songListId));
    }
    @RequestMapping(value = "/update")
    public Object updateListSong(@RequestBody ListSong listSong) {
        return listSongService.update(listSong);
    }
    @RequestMapping(value="/delete")
    public Object deleteListSong(HttpServletRequest request){
        String id=request.getParameter("id");
        return listSongService.delete(Integer.parseInt(id));
    }
    @RequestMapping(value="/allListSong")
    public Object allListSong(){
        return listSongService.allListSong();
    }
}
