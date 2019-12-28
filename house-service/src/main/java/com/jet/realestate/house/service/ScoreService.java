package com.jet.realestate.house.service;

import com.jet.realestate.house.model.Score;

import java.util.List;

public interface ScoreService {
    Score insert(Score score);

    List<Score> queryScores(Long houseId);

    Score queryScore(Long houseId,Long vendeeId);
}
