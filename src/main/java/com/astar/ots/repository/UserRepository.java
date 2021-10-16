package com.astar.ots.repository;

import com.astar.ots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);
  User findByUsername(String username);
}
