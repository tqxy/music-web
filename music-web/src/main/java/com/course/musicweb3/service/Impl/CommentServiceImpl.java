package com.course.musicweb3.service.Impl;

import com.course.musicweb3.dao.CommentMapper;
import com.course.musicweb3.domain.Comment;
import com.course.musicweb3.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentMapper commentMapper;
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return commentMapper.delete(id)>0;
    }

    @Override
    public boolean update(Comment comment) {
        return commentMapper.update(comment)>0;
    }

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> allComment() {
        return commentMapper.allComment();
    }

    @Override
    public List<Comment> commentOfSongId(Integer songId) {
        return commentMapper.commentOfSongId(songId);
    }

    @Override
    public List<Comment> commentOfSongListId(Integer songListId) {
        return commentMapper.commentOfSongListId(songListId);
    }
}
