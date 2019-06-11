package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.Audio;
import com.bewatches.server.Service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    AudioService audioService;

   /* @RequestMapping(method = RequestMethod.GET)
    public Audio getAudio(){
        return audioService.save();
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public Audio saveAudio(@RequestBody Audio audio){
        return audioService.save(audio);
    }
}
