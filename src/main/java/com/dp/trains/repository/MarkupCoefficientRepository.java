package com.dp.trains.repository;

import com.dp.trains.model.entities.MarkupCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MarkupCoefficientRepository extends JpaRepository<MarkupCoefficientEntity, Long> {

    int countByYear(int year);

    Collection<MarkupCoefficientEntity> findAllByYear(Integer previousYear);
}
