package com.dp.trains.repository;

import com.dp.trains.model.entities.StrategicCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategicCoefficientRepostiory extends JpaRepository<StrategicCoefficientEntity, Long> {
}
