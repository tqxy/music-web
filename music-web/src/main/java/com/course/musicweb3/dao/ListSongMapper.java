package com.course.musicweb3.dao;

import com.course.musicweb3.domain.ListSong;

import java.util.List;

public interface ListSongMapper {
    public int insert(ListSong listSong);
    public int update(ListSong listSong);
    public int delete(Integer id);
    public ListSong selectByPrimaryKey(Integer id);
    public List<ListSong> allListSong();
    public List<ListSong> listSongOfListId(Integer ListId);
}
