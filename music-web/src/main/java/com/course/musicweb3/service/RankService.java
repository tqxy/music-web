package com.course.musicweb3.service;

import com.course.musicweb3.domain.Rank;

public interface RankService {
    public int insert(Rank rank);
    public int selectScoreSum(int songListId);
    public int selectRankNum(int songListId);
    public float rankOfSongListId(Integer songListId);
}
