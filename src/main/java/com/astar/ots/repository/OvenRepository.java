package com.astar.ots.repository;

import com.astar.ots.entity.Oven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvenRepository extends JpaRepository<Oven, Long> { }
