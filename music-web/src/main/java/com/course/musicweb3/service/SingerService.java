package com.course.musicweb3.service;

import com.course.musicweb3.domain.Singer;

import java.util.List;

public interface SingerService {
    public boolean insert(Singer singer);
    public boolean update(Singer singer);
    public boolean delete(Integer id);
    public Singer selectByPrimaryKey(Integer id);
    public List<Singer> allSinger();
    public List<Singer> singerOfName(String name);
    public List<Singer> singerOfSex(Integer sex);
}
