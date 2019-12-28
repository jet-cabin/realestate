package com.jet.realestate.house.mapper;

import com.jet.realestate.house.model.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {

    int insert(Score score);

    List<Score> queryScores(@Param("houseId") Long houseId);

    Score queryScore(@Param("houseId") Long houseId, @Param("userId") Long vendeeId);
}
