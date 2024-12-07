package com.course.musicweb3.service.Impl;

import com.course.musicweb3.dao.ListSongMapper;
import com.course.musicweb3.domain.ListSong;
import com.course.musicweb3.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {
    private final ListSongMapper listSongMapper;
    @Autowired
    public ListSongServiceImpl(ListSongMapper listSongMapper) {
        this.listSongMapper = listSongMapper;
    }
    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return listSongMapper.delete(id)>0;
    }

    @Override
    public ListSong selectByPrimaryKey(Integer id) {
        return listSongMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    @Override
    public List<ListSong> listSongOfListId(Integer ListId) {
        return listSongMapper.listSongOfListId(ListId);
    }
}
