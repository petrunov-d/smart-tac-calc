package com.dp.trains.repository;

import com.dp.trains.model.entities.LineNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineNumberRepository extends JpaRepository<LineNumberEntity, Long> {
}
