package com.astar.ots;

import com.astar.ots.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAsync
@EnableWebMvc
@SpringBootApplication
public class OvenTemperatureStoreApplication implements ApplicationRunner {

	@Autowired
	private DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(OvenTemperatureStoreApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		dataService.doInsertAppUsers();
		dataService.doInsertOvenTemperatureHistoricalData();
	}
}
