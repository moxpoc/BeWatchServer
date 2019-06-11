package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.BeatHeart;
import com.bewatches.server.Service.BeatHeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beatheart")
public class BeatHeartController {

    @Autowired
    BeatHeartService beatHeartService;

    @RequestMapping(method = RequestMethod.POST)
    public BeatHeart saveBeatHeart(@RequestBody BeatHeart beatHeart){
        return beatHeartService.save(beatHeart);
    }
}
