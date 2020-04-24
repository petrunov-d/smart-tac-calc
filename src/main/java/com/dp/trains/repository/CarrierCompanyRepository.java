package com.dp.trains.repository;

import com.dp.trains.model.entities.CarrierCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CarrierCompanyRepository extends JpaRepository<CarrierCompanyEntity, Long> {

    int countByYear(int year);

    Collection<CarrierCompanyEntity> findAllByYear(Integer previousYear);

    Collection<CarrierCompanyEntity> findAllByCarrierName(String carrierName);
}
