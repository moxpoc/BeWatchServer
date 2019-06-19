package com.bewatches.server.Repository;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchRepository extends JpaRepository<Watch, Long> {
    List<Watch> getAllByClient(Client client);
    Watch getByImei(String imei);
    void deleteByImei(String imei);

}
