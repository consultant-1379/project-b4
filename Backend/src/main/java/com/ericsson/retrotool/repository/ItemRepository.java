package com.ericsson.retrotool.repository;

import com.ericsson.retrotool.model.RetroItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<RetroItem, Integer> {

}

