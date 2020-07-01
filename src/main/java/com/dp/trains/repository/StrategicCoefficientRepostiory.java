package com.dp.trains.repository;

import com.dp.trains.model.entities.StrategicCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategicCoefficientRepostiory extends JpaRepository<StrategicCoefficientEntity, Long> {

    int countByYear(int year);

    List<StrategicCoefficientEntity> findAllByYear(Integer previousYear);
}