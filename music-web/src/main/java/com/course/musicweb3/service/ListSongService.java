package com.course.musicweb3.service;

import com.course.musicweb3.domain.ListSong;

import java.util.List;

public interface ListSongService {
    public boolean insert(ListSong listSong);
    public boolean update(ListSong listSong);
    public boolean delete(Integer id);
    public ListSong selectByPrimaryKey(Integer id);
    public List<ListSong> allListSong();
    public List<ListSong> listSongOfListId(Integer ListId);
}
