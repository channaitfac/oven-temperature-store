package com.astar.ots.service;

import com.astar.ots.entity.Oven;
import com.astar.ots.repository.OvenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OvenService {

    @Autowired
    private OvenRepository ovenRepository;

    public Optional<Oven> save(Oven oven) {
        return Optional.of(ovenRepository.save(oven));
    }

    public Optional<Oven> getById(Long id) {
        return Optional.ofNullable(ovenRepository.getById(id));
    }

    public Optional<List<Oven>> findAll() {
        return Optional.ofNullable(ovenRepository.findAll());
    }
}
