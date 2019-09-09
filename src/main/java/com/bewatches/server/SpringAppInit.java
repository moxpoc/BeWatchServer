package com.bewatches.server;

import com.velesov84.application.AppMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAppInit {
    public static void  main(String[] args){
        SpringApplication.run(SpringAppInit.class, args);
        AppMain instance = new AppMain();
        instance.execute();
    }
}
