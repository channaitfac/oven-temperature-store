package com.astar.ots.service;

import com.astar.ots.entity.Temperature;
import com.astar.ots.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {

    @Autowired
    private TemperatureRepository temperatureRepository;

    public Optional<Temperature> save(Temperature temperature) {
        return Optional.of(temperatureRepository.save(temperature));
    }

    public void deleteAll() {
        temperatureRepository.deleteAll();
    }
}
