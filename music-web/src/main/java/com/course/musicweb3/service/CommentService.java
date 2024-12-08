package com.course.musicweb3.service;

import com.course.musicweb3.domain.Comment;

import java.util.List;


public interface CommentService {
    public boolean insert(Comment comment);
    public boolean delete(Integer id);
    public boolean update(Comment comment);
    public Comment selectByPrimaryKey(Integer id);
    public List<Comment> allComment();
    public List<Comment> commentOfSongId(Integer songId);
    public List<Comment> commentOfSongListId(Integer songListId);
}
