package com.wayne.javaexercise.service;

public class CachedRate {

    public CachedRate(double rate, long timestamp) {
        this.rate = rate;
        this.timestamp = timestamp;
    }

    public double rate;

    public long timestamp;
}
