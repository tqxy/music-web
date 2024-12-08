package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Collect;
import com.course.musicweb3.service.CollectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectController {
    private final CollectService collectService;
    @Autowired
    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }
    @RequestMapping("/add")
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId").trim();
        String type = request.getParameter("type").trim();
        String songId = request.getParameter("songId").trim();
        if(songId==null||songId.equals("")) {
            jsonObject.put("code",0);
            jsonObject.put("msg","收藏歌曲失败");
            return jsonObject;
        }
        else if(collectService.existSongId(Integer.parseInt(userId),Integer.parseInt(songId))){
            jsonObject.put("code",2);
            jsonObject.put("msg","已收藏");
            return jsonObject;
        }

        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        collect.setType(Byte.parseByte(type));
        collect.setSongId(Integer.parseInt(songId));
        boolean flag = collectService.insert(collect);

        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","收藏成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","收藏失败");
        }
        return jsonObject;

    }
    @RequestMapping("/delete")
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return collectService.delete(Integer.parseInt(id));
    }
    @RequestMapping("/allCollect")
    public Object allCollect(){
        return collectService.allCollect();
    }
    @RequestMapping("/detail")
    public Object collectOfUserId(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();
        return collectService.collectOfUserId(Integer.parseInt(userId));
    }
}
