package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Rank;
import com.course.musicweb3.service.RankService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rank")
public class RankController {
    private final RankService rankService;

    @Autowired
    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @RequestMapping("/add")
    public Object addRank(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String songListId = request.getParameter("songId").trim();
        String consumerId = request.getParameter("consumerId").trim();
        String score = request.getParameter("score").trim();
        boolean flag = rankService.insert(new Rank(null, Integer.parseInt(songListId), Integer.parseInt(consumerId), Integer.parseInt(score))) > 0;
        if (flag) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "评价成功");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "评价失败");
        }
        return jsonObject;
    }
    @RequestMapping("/average")
    public Object averageScoreOfSongListId(HttpServletRequest request) {
        String songListId = request.getParameter("songListId").trim();
        return rankService.rankOfSongListId(Integer.parseInt(songListId));
    }
}
