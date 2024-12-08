package com.course.musicweb3.dao;

import com.course.musicweb3.domain.Rank;
import org.springframework.stereotype.Repository;

@Repository
public interface RankMapper {
    public int insert(Rank rank);
    public int selectScoreSum(Integer songListId);
    public int selectRankNum(Integer songListId);
}
