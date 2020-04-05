package com.dp.trains.repository;

import com.dp.trains.model.entities.LineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineTypeRepository extends JpaRepository<LineTypeEntity, Long> {

    int countByYear(int year);

    List<LineTypeEntity> findAllByYear(Integer previousYear);

    LineTypeEntity findByLineType(String lineType);
}
