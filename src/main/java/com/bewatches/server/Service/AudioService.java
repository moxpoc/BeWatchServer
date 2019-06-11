package com.bewatches.server.Service;

import com.bewatches.server.Model.Parent.Audio;

import java.util.List;

public interface AudioService {
    List<Audio> getAll();
    Audio save(Audio audio);

}
