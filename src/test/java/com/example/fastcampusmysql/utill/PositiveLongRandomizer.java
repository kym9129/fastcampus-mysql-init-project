package com.example.fastcampusmysql.utill;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

public class PositiveLongRandomizer implements Randomizer<Long> {
    @Override
    public Long getRandomValue() {
        return Math.abs(new Random().nextLong());
    }
}
