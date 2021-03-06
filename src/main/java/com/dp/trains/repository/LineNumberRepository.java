package com.dp.trains.repository;

import com.dp.trains.model.entities.LineNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineNumberRepository extends JpaRepository<LineNumberEntity, Long> {

    int countByYear(int year);

    List<LineNumberEntity> findAllByYear(int year);

    LineNumberEntity findByLineNumber(Integer lineNUmber);
}
