package com.barber.shop;

import com.barber.shop.util.StorageCloudnary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarbershopApplication implements CommandLineRunner {
    
    @Autowired
    private StorageCloudnary cloudnary;
    
    public static void main(String[] args) {
        SpringApplication.run(BarbershopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //cloudnary.getFolders();
    }
    
}
