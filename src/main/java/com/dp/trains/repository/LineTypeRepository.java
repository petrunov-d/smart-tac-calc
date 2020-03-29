package com.dp.trains.repository;

import com.dp.trains.model.entities.LineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineTypeRepository extends JpaRepository<LineTypeEntity, Long> {
}
