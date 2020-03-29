package com.dp.trains.repository;

import com.dp.trains.model.entities.TrafficDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficDataRepository extends JpaRepository<TrafficDataEntity, Long> {
}
