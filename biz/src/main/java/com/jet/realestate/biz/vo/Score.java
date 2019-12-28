package com.jet.realestate.biz.vo;

import lombok.Data;

import java.util.Map;

@Data
public class Score {
    private Map<Integer,Float> agg;

    private int curUserScore;
}
