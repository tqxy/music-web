package com.course.musicweb3.dao;

import com.course.musicweb3.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper {
    public int insert(Collect collect);
    public int delete(Integer id);
    public List<Collect> allCollect();
    public List<Collect> collectOfUserId(Integer userId);
    public boolean existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

}
