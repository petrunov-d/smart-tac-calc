package com.dp.trains.repository;

import com.dp.trains.model.entities.UnitPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitPriceRepository extends JpaRepository<UnitPriceEntity, Long> {

    List<UnitPriceEntity> findAllByYearBetween(int start, int end);
}
