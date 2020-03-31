package com.dp.trains.repository;

import com.dp.trains.model.entities.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {

    Set<SectionEntity> findAllByFirstKeyPoint(String firstKeyPoint);

    int countByYear(int year);

    List<SectionEntity> findAllByYear(Integer previousYear);
}
