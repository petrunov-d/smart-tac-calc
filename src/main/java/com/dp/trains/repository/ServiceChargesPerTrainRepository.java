package com.dp.trains.repository;

import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceChargesPerTrainRepository extends JpaRepository<ServiceChargesPerTrainEntity, Long> {

    List<ServiceChargesPerTrainEntity> findByTrainNumberAndRailStationEntity(Integer trainNumber, RailStationEntity railStationEntity);
}
