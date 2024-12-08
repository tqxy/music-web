package com.course.musicweb3.service;

import com.course.musicweb3.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {
    public boolean insert(Collect collect);
    public boolean delete(Integer id);
    public List<Collect> allCollect();
    public List<Collect> collectOfUserId(Integer userId);
    public boolean existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);
}
