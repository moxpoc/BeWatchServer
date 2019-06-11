package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.Parent.Audio;
import com.bewatches.server.Repository.AudioRepository;
import com.bewatches.server.Service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioServiceImpl implements AudioService {

    @Autowired
    AudioRepository audioRepository;

    public Audio save(Audio audio){
        return audioRepository.saveAndFlush(audio);
    }
}
