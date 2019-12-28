package com.jet.realestate.house.controller;

import com.jet.realestate.common.api.CommonResult;
import com.jet.realestate.house.model.Score;
import com.jet.realestate.house.service.ScoreService;
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
    private ScoreService service;

    @PostMapping
    public CommonResult<Score> doScore(@RequestBody Score score) {
        service.insert(score);

        return CommonResult.success(score);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Score> queryScores(@RequestParam("houseId") Long houseId) {
        return service.queryScores(houseId);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Score queryScore(@RequestParam("houseId") Long houseId, @RequestParam("userId") Long userId) {
        return service.queryScore(houseId, userId);
    }

//    @GetMapping("/aggregation")
//    public CommonResult<Score> queryScoreAggragation(@RequestParam("userId") Long userId, @RequestParam("houseId") Long houseId) {
//        List<Score> scores = service.queryScores(houseId);
//
//        Map<Integer, Float> map = new HashMap<>();
//        int size = scores.size();
//
//        if (size > 0) {
//            IntStream.range(1, 6).forEach((i) -> {
//                map.put(i, (float) scores.stream().filter(s -> s.getGrade().equals(i)).count() / size);
//            });
//        }
//
//        Score score = new Score();
//        score.setAgg(map);
//
//        Score s = service.queryScore(houseId,userId);
//        score.setCurUserScore(s == null ? 0 : s.getGrade());
//
//        return CommonResult.success(score);
//    }
}
