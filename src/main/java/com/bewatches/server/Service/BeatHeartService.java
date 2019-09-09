package com.bewatches.server.Service;

import com.bewatches.server.Model.Parent.BeatHeart;

public interface BeatHeartService {
    BeatHeart save(BeatHeart beatHeart);
    BeatHeart getBeatheartByImei(String imei);
}
