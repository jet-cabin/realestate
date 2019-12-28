package com.jet.realestate.house.service.impl;

import com.jet.realestate.house.mapper.ScoreMapper;
import com.jet.realestate.house.model.Score;
import com.jet.realestate.house.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public Score insert(Score score) {

       scoreMapper.insert(score);
        return score;
    }

    @Override
    public List<Score> queryScores(Long houseId) {
        return scoreMapper.queryScores(houseId);
    }

    @Override
    public Score queryScore(Long houseId,Long vendeeId) {
        return scoreMapper.queryScore(houseId,vendeeId);
    }
}
