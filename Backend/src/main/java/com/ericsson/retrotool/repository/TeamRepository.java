package com.ericsson.retrotool.repository;

import com.ericsson.retrotool.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
