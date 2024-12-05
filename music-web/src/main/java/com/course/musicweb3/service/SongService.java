package com.course.musicweb3.service;

import com.course.musicweb3.domain.Song;

import java.util.List;

public interface SongService {
    public boolean insert(Song song);
    public boolean update(Song song);
    public boolean delete(Integer id);
    public Song selectByPrimaryKey(Integer id);
    public List<Song> allSong();
    public List<Song> songOfName(String name);
    public List<Song> songOfSingerId(Integer singerId);
}
