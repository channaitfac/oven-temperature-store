package com.astar.ots.service;

import com.astar.ots.entity.Oven;
import com.astar.ots.entity.Temperature;
import com.astar.ots.entity.User;
import com.astar.ots.util.Role;
import com.astar.ots.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.astar.ots.util.Constants.UserDetail.*;

@Slf4j
@Service
public class DataService {

    @Autowired
    private UserService userService;

    @Autowired
    private OvenService ovenService;

    @Autowired
    private TemperatureService temperatureService;

    @Async
    public void doInsertAppUsers() {

        User admin = new User();
        admin.setUsername(USERNAME);
        admin.setPassword(PASSWORD);
        admin.setEmail(EMAIL);
        admin.setRoles(Collections.singletonList(Role.ROLE_ADMIN));

        try {
            userService.signup(admin);
        } catch (Exception e) {
            log.error("Exception occurred while inserting application users: {}", e.getLocalizedMessage());
        }
    }

    @Async
    public void doInsertOvenTemperatureHistoricalData() {

        // Delete old data
        temperatureService.deleteAll();
        ovenService.deleteAll();

        // Insert new data
        Random random = new Random();

        for(int i = 0; i < 10; i++) {

            Oven oven = new Oven();
            oven.setCode("OVN-" + (random.nextInt(900) + 100) + "-" + (random.nextInt(900) + 100));
            oven.setDescription("This is " + (i + 1) + " Oven");

            // Insert Oven
            ovenService.save(oven).ifPresent(e -> {

                // Insert Temperatures for that Oven
                for(int j = 0; j < 3; j++) {

                    Temperature temperature = new Temperature();
                    temperature.setOven(e);
                    temperature.setValue((float) random.nextInt(89) + 10);
                    temperature.setDescription("This is " + (j + 1) + " temperature");
                    temperature.setReportedOn(Util.removeMilliseconds(new Date(new Date().getTime() + ((j * 40) * 60 * 100))));

                    temperatureService.save(temperature);
                }
            });
        }
    }
}
