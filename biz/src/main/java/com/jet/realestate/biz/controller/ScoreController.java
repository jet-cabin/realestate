package com.jet.realestate.biz.controller;

import com.jet.realestate.biz.client.ScoreClient;
import com.jet.realestate.biz.model.Score;
//import com.jet.realestate.biz.service.ScoreService;
import com.jet.realestate.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/v1/score")
public class ScoreController {

    @Autowired
    private ScoreClient scoreClient;

    @PostMapping
    public CommonResult<Score> doScore(@RequestBody Score score) {
        scoreClient.insert(score);

        return CommonResult.success(score);
    }

    @GetMapping("/aggregation")
    public CommonResult<com.jet.realestate.biz.vo.Score> queryScoreAggragation(@RequestParam("userId") Long userId, @RequestParam("houseId") Long houseId) {
        List<Score> scores = scoreClient.queryScores(houseId);

        Map<Integer, Float> map = new HashMap<>();
        int size = scores.size();

        if (size > 0) {
            IntStream.range(1, 6).forEach((i) -> {
                map.put(i, (float) scores.stream().filter(s -> s.getGrade().equals(i)).count() / size);
            });
        }

        com.jet.realestate.biz.vo.Score score = new com.jet.realestate.biz.vo.Score();
        score.setAgg(map);

        Score s = scoreClient.queryScore(houseId,userId);
        score.setCurUserScore(s == null ? 0 : s.getGrade());

        return CommonResult.success(score);
    }
}
