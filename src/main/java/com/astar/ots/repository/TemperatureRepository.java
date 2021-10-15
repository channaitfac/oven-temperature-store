package com.astar.ots.repository;

import com.astar.ots.entity.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {

    List<Temperature> findAllByReportedOnBetween(Date startDate, Date endDate);
}
