package com.tennis.platform.repository;

import com.tennis.platform.model.Coach;
import com.tennis.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByUser(User user);
}