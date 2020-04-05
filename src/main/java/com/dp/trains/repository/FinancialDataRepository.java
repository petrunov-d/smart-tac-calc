package com.dp.trains.repository;

import com.dp.trains.model.entities.FinancialDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialDataRepository extends JpaRepository<FinancialDataEntity, Long> {

    FinancialDataEntity findByCode(Integer code);
}
