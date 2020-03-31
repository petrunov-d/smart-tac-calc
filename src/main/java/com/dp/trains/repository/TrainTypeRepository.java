package com.dp.trains.repository;

import com.dp.trains.model.entities.TrainTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainTypeRepository extends JpaRepository<TrainTypeEntity, Long> {

    int countByYear(int year);

    List<TrainTypeEntity> findAllByYear(Integer previousYear);
}
