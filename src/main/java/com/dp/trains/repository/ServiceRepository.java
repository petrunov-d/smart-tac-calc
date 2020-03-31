package com.dp.trains.repository;

import com.dp.trains.model.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    int countByYear(int year);

    List<ServiceEntity> findAllByYear(Integer previousYear);
}
