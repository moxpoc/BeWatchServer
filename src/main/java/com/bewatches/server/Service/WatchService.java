package com.bewatches.server.Service;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Model.Client;

import java.util.List;

public interface WatchService {

    List<Watch> getAllByClient(Client client);
    Watch getByImei(String imei);
    Watch getWatchByClient(Client client);
    Watch save(Watch watch);
    void deleteByImei(String imei);

}
