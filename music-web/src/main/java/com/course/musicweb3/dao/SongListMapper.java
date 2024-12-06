package com.course.musicweb3.dao;

import com.course.musicweb3.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListMapper {
    public int insert(SongList songList);
    public int update(SongList songList);
    public int delete(Integer id);
    public SongList selectByPrimaryKey(Integer id);
    public List<SongList> allSongList();
    //精确查询
    public List<SongList> songListOfTitle(String title);
    //模糊查询
    public List<SongList> likeTitle(String title);
    //根据风格
    public List<SongList> likeStyle(String style);
}
