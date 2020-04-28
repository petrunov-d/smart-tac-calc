package com.dp.trains.repository;

import com.dp.trains.model.entities.TaxForServicesPerTrainEntity;
import com.dp.trains.model.entities.TaxPerTrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxForServicesPerTrainRepository extends JpaRepository<TaxForServicesPerTrainEntity, Long> {
}
