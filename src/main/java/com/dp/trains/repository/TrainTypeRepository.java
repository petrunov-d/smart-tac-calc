package com.dp.trains.repository;

import com.dp.trains.model.entities.TrainTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTypeRepository extends JpaRepository<TrainTypeEntity, Long> {
}
