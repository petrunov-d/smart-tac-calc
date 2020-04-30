package com.dp.trains.repository;

import com.dp.trains.model.entities.TaxPerTrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxPerTrainRepository extends JpaRepository<TaxPerTrainEntity, Long> {

    List<TaxPerTrainEntity> findAllByTrainNumber(Integer trainNumber);

    void deleteAllByTrainNumber(Integer trainNumber);
}
