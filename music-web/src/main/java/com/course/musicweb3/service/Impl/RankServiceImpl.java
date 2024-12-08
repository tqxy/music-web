package com.course.musicweb3.service.Impl;

import com.course.musicweb3.domain.Rank;
import com.course.musicweb3.service.RankService;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl implements RankService {
    private com.course.musicweb3.dao.RankMapper rankMapper;

    public RankServiceImpl(com.course.musicweb3.dao.RankMapper rankMapper) {
        this.rankMapper = rankMapper;
    }

    @Override
    public int insert(Rank rank) {
        return rankMapper.insert(rank);
    }

    @Override
    public float rankOfSongListId(Integer songListId) {
        int rankNum=selectRankNum(songListId);
        if(rankNum==0){
            return 5;
        }
        return (float) selectScoreSum(songListId) /rankNum;
    }
    @Override
    public int selectRankNum(int songListId) {
        return rankMapper.selectRankNum(songListId);
    }
    @Override
    public int selectScoreSum(int songListId) {
        return rankMapper.selectScoreSum(songListId);
    }
}
