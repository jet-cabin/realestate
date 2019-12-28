package com.jet.realestate.biz.client;

import com.jet.realestate.biz.model.Score;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(serviceId = "house-service",path = "/v1/score")
public interface ScoreClient {

    @RequestMapping(value = "",method = RequestMethod.POST)
    void insert(@RequestBody Score score);

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    List<Score> queryScores(@RequestParam("houseId") Long houseId);

    @RequestMapping(value = "",method = RequestMethod.GET)
    Score queryScore(@RequestParam("houseId") Long houseId,@RequestParam("userId") Long userId);
}
