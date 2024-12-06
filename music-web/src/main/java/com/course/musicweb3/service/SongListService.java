package com.course.musicweb3.service;

import com.course.musicweb3.domain.SongList;

import java.util.List;

public interface SongListService {
    public boolean insert(SongList songList);
    public boolean update(SongList songList);
    public boolean delete(Integer id);
    public SongList selectByPrimaryKey(Integer id);
    public List<SongList> allSongList();
    //精确查询
    public List<SongList> songListOfTitle(String title);
    //模糊查询
    public List<SongList> likeTitle(String title);
    //根据风格
    public List<SongList> likeStyle(String style);
}
