package com.dp.trains.repository;

import com.dp.trains.model.entities.SubSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSectionRepository extends JpaRepository<SubSectionEntity, Long> {
}
