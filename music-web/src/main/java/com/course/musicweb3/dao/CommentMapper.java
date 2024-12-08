package com.course.musicweb3.dao;

import com.course.musicweb3.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    public int insert(Comment comment);
    public int delete(Integer id);
    public int update(Comment comment);
    public Comment selectByPrimaryKey(Integer id);
    public List<Comment> allComment();
    public List<Comment> commentOfSongId(Integer songId);
    public List<Comment> commentOfSongListId(Integer songListId);
}
