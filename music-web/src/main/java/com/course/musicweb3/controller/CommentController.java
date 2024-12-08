package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Comment;
import com.course.musicweb3.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @RequestMapping("/add")
    public Object addComment(HttpServletRequest request) {
        JSONObject jsonObject=new JSONObject();
        String userId=request.getParameter("userId").trim();
        String type=request.getParameter("type").trim();
        String songId=request.getParameter("songId");
        String songListId=request.getParameter("songListId");
        if(songId==null||songId.equals("")){
            songId=null;
        }else{
            songId=songId.trim();
        }
        if(songListId==null||songListId.equals("")){
            songListId=null;
        }else{
            songListId=songListId.trim();
        }
        String content=request.getParameter("content").trim();


        //保存到歌手的对象中
        Comment comment=new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(Byte.parseByte(type));
        if(type.equals("0")) {
            comment.setSongId(Integer.parseInt(songId));
        }else{
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);

        boolean flag = commentService.insert(comment);
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","评论成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","评论失败");
        }
        return jsonObject;
    }
    @RequestMapping("/update")
    public Object updateComment(HttpServletRequest request) {
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String userId=request.getParameter("userId").trim();
        String type=request.getParameter("type").trim();
        String songId=request.getParameter("songId").trim();
        String songListId=request.getParameter("songListId").trim();
        String content=request.getParameter("content").trim();


        //保存到对象中
        Comment comment=new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(Byte.parseByte(type));
        if(songId!=null&&!songId.equals("")){
            songId=null;
        }else{
            comment.setSongId(Integer.parseInt(songId));
        }
        if(songListId!=null&&!songListId.equals("")){
            songListId=null;
        }else{
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);

        boolean flag = commentService.update(comment);
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","评论更改成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","评论更改失败");
        }
        return jsonObject;
    }
    @RequestMapping("/all")
    public Object allComment() {
        return commentService.allComment();
    }
    @RequestMapping("/song/detail")
    public Object commentOfSongId(HttpServletRequest request) {
        String songId = request.getParameter("songId").trim();
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }
    @RequestMapping("/songList/detail")
    public Object commentOfSongListId(HttpServletRequest request) {
        String songListId = request.getParameter("songListId").trim();
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }
    @RequestMapping("/like")
    public Object likeComment(HttpServletRequest request) {
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String up=request.getParameter("up").trim();

        Comment comment=new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));
        boolean flag = commentService.update(comment);
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("msg","点赞成功");
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","点赞失败");
        }
        return jsonObject;
    }
}
