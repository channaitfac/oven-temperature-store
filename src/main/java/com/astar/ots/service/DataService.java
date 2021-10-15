package com.astar.ots.service;

import com.astar.ots.entity.Oven;
import com.astar.ots.entity.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;

@Service
public class DataService {

    @Autowired
    private OvenService ovenService;

    @Autowired
    private TemperatureService temperatureService;

    @Async
    public void doInsertHistoricalData() {

        Random random = new Random();
        Calendar calendar = Calendar.getInstance();

        for(int i = 0; i < 10; i++) {

            Oven oven = new Oven();
            oven.setCode("OVN-" + (random.nextInt(900) + 100) + "-" + (random.nextInt(900) + 100));
            oven.setDescription("This is " + (i + 1) + " Oven");

            // Insert Oven
            ovenService.save(oven).ifPresent(e -> {

                // Insert Temperatures for that Oven
                for(int j = 0; j < 3; j++) {

                    calendar.add(Calendar.MINUTE, (j * 30));

                    Temperature temperature = new Temperature();
                    temperature.setOven(e);
                    temperature.setValue((float) random.nextInt(89) + 10);
                    temperature.setDescription("This is " + (j + 1) + " temperature");
                    temperature.setReportedOn(calendar.getTime());

                    temperatureService.save(temperature);
                }
            });
        }
    }
}
