package com.course.musicweb3.service.Impl;

import com.course.musicweb3.dao.CollectMapper;
import com.course.musicweb3.domain.Collect;
import com.course.musicweb3.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    private final CollectMapper collectMapper;

    @Autowired
    public CollectServiceImpl(CollectMapper collectMapper) {
        this.collectMapper = collectMapper;
    }

    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id) > 0;
    }

    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    @Override
    public List<Collect> collectOfUserId(Integer userId) {
        return collectMapper.collectOfUserId(userId);
    }

    @Override
    public boolean existSongId(Integer userId, Integer songId) {
        return collectMapper.existSongId(userId, songId);
    }
}
