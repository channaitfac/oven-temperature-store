package com.astar.ots.service;

import com.astar.ots.entity.Temperature;
import com.astar.ots.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TemperatureService {

    @Autowired
    private TemperatureRepository temperatureRepository;

    public Optional<Temperature> save(Temperature temperature) {
        return Optional.of(temperatureRepository.save(temperature));
    }

    public Optional<Temperature> getById(Long id) {
        return Optional.ofNullable(temperatureRepository.getById(id));
    }

    public Optional<List<Temperature>> findAll() {
        return Optional.ofNullable(temperatureRepository.findAll());
    }

    public Optional<List<Temperature>> findAllByReportedOnBetween(Date startDate, Date endDate) {
        return Optional.ofNullable(temperatureRepository.findAllByReportedOnBetween(startDate, endDate));
    }
}
