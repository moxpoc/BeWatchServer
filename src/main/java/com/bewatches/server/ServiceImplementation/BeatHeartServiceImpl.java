package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.Parent.BeatHeart;
import com.bewatches.server.Repository.BeatHeartRepository;
import com.bewatches.server.Service.BeatHeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeatHeartServiceImpl implements BeatHeartService {

    @Autowired
    BeatHeartRepository beatHeartRepository;

    public BeatHeart save(BeatHeart beatHeart){
        return beatHeartRepository.saveAndFlush(beatHeart);
    }
}
