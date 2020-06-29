package com.dp.trains.services;

import com.dp.trains.model.dto.ServiceChargesPerTrainDto;
import com.dp.trains.model.dto.report.ServiceChargesPerTrainReportDto;
import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.repository.ServiceChargesPerTrainRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceChargesPerTrainService {

    private final ServiceChargesPerTrainRepository serviceChargesPerTrainRepository;

    private final RailStationService railStationService;

    @Transactional
    public ServiceChargesPerTrainEntity save(ServiceChargesPerTrainEntity serviceChargesPerTrainEntity) {

        return this.serviceChargesPerTrainRepository.save(serviceChargesPerTrainEntity);
    }

    @Transactional(readOnly = true)
    public Integer getCountByTrainNumber(Integer trainNumber) {

        return serviceChargesPerTrainRepository.countAllByTrainNumber(trainNumber);
    }

    @Transactional(readOnly = true)
    public Set<Integer> getAllTrainNumbersWithServiceCharges() {

        return this.serviceChargesPerTrainRepository.findAll().stream()
                .map(ServiceChargesPerTrainEntity::getTrainNumber)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional(readOnly = true)
    public List<ServiceChargesPerTrainEntity> findByTrainNumberAndRailRoadStation(Integer trainNumber,
                                                                                  String railRoadStation) {

        RailStationEntity railStationEntity = this.railStationService.getByRailStationName(railRoadStation);

        if (railStationEntity == null) {

            log.info("No railstation for" + trainNumber + " " + railRoadStation);

            return Lists.newArrayList();
        }

        return this.serviceChargesPerTrainRepository
                .findByTrainNumberAndRailStationEntity(trainNumber, railStationEntity);
    }

    public Double getTotalServiceChargesForTrainNumberAndRailStation(List<ServiceChargesPerTrainEntity>
                                                                             serviceChargesPerTrainEntities) {

        return serviceChargesPerTrainEntities
                .stream()
                .map(x -> x.getServiceEntity().getUnitPrice() * x.getServiceCount())
                .reduce(0.0, Double::sum);
    }

    @Transactional(readOnly = true)
    public int count() {

        int count = (int) serviceChargesPerTrainRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<ServiceChargesPerTrainEntity> fetch(int offset, int limit) {

        return serviceChargesPerTrainRepository.findAll();
    }

    @Transactional
    public void deleteAll() {

        serviceChargesPerTrainRepository.deleteAll();
    }

    @Transactional
    public void remove(ServiceChargesPerTrainEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        serviceChargesPerTrainRepository.delete(item);
    }

    @Transactional
    public ServiceChargesPerTrainEntity update(ServiceChargesPerTrainEntity serviceChargesPerTrainEntity) {

        Optional<ServiceChargesPerTrainEntity> optional = Optional.of(serviceChargesPerTrainRepository
                .findById(serviceChargesPerTrainEntity.getId())).orElseThrow(IllegalStateException::new);

        ServiceChargesPerTrainEntity serviceChargesPerTrainEntityFromDb = optional.get();

        log.info("About to update item " + serviceChargesPerTrainEntityFromDb.toString()
                + " to " + serviceChargesPerTrainEntity.toString());

        serviceChargesPerTrainEntityFromDb.setServiceCount(serviceChargesPerTrainEntity.getServiceCount());

        return serviceChargesPerTrainRepository.save(serviceChargesPerTrainEntityFromDb);
    }

    @Transactional
    public ServiceChargesPerTrainEntity update(ServiceChargesPerTrainDto serviceDto, Long id) {

        Optional<ServiceChargesPerTrainEntity> optional = Optional.of(serviceChargesPerTrainRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        ServiceChargesPerTrainEntity serviceChargesPerTrainEntityFromDb = optional.get();

        log.info("About to update item " + serviceChargesPerTrainEntityFromDb.toString()
                + " to " + serviceDto.toString());

        serviceChargesPerTrainEntityFromDb.setServiceCount(serviceDto.getServiceCount());

        return serviceChargesPerTrainRepository.save(serviceChargesPerTrainEntityFromDb);
    }

    @Transactional(readOnly = true)
    public List<ServiceChargesPerTrainReportDto> getReportDtosForTrainNumber(Integer trainNumber) {

        List<ServiceChargesPerTrainEntity> entities = this.serviceChargesPerTrainRepository.findAllByTrainNumber(trainNumber);

        return entities.stream()
                .map(x -> ServiceChargesPerTrainReportDto
                        .builder()
                        .trainNumber(x.getTrainNumber())
                        .railStationName(x.getRailStationEntity().getStation())
                        .railStationType(x.getRailStationEntity().getType())
                        .serviceName(x.getServiceEntity().getName())
                        .serviceCount(x.getServiceCount())
                        .totalPrice(BigDecimal.valueOf(x.getServiceCount() * x.getServiceEntity().getUnitPrice()))
                        .servicePrice(x.getServiceEntity().getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}