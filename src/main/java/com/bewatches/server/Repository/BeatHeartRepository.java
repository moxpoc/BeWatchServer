package com.bewatches.server.Repository;

import com.bewatches.server.Model.Parent.BeatHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeatHeartRepository extends JpaRepository<BeatHeart, Long> {
    BeatHeart getBeatHeartByImei(String imei);
}
