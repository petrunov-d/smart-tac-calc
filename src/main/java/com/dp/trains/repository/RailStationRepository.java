package com.dp.trains.repository;

import com.dp.trains.model.entities.RailStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RailStationRepository extends JpaRepository<RailStationEntity, Long> {

    List<RailStationEntity> findByStation(String station);
}