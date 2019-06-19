package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Model.Client;
import com.bewatches.server.Repository.WatchRepository;
import com.bewatches.server.Service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchServiceImpl implements WatchService {

    @Autowired
    WatchRepository watchRepository;

    public List<Watch> getAllByClient(Client client){
        return watchRepository.getAllByClient(client);
    }

    public Watch getByImei(String imei){
        return watchRepository.getByImei(imei);
    }

    public Watch save(Watch watch){
        return watchRepository.saveAndFlush(watch);
    }

    public void deleteByImei(String imei){
        watchRepository.deleteByImei(imei);
    }
}
