package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.ServiceDto;
import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.repository.ServiceRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceService implements BaseImportService {

    private final ServiceRepository serviceRepository;
    private final ObjectMapper defaultObjectMapper;

    @Qualifier("serviceMapper")
    private final DefaultDtoEntityMapperService<ServiceDto, ServiceEntity> serviceMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) serviceRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<ServiceEntity> fetch(int offset, int limit) {

        return serviceRepository.findAll();
    }

    @Transactional
    public void remove(ServiceEntity serviceEntity) {

        log.info(this.getClass().getSimpleName() + " delete: " + serviceEntity.toString());

        serviceRepository.delete(serviceEntity);
    }

    @Transactional
    public ServiceEntity add(ServiceDto serviceDto) {

        ServiceEntity serviceEntity = serviceMapper.mapEntity(serviceDto);

        log.info(this.getClass().getSimpleName() + " add: " + serviceEntity.toString());

        return serviceRepository.save(serviceEntity);
    }

    @Transactional
    public void add(Collection<ServiceDto> serviceDtos) {

        Collection<ServiceEntity> serviceEntities = serviceMapper.mapEntities(serviceDtos);

        serviceRepository.saveAll(serviceEntities);
    }

    @Transactional
    public ServiceEntity update(ServiceEntity serviceEntity) {

        Optional<ServiceEntity> optional = Optional.of(serviceRepository
                .findById(serviceEntity.getId())).orElseThrow(IllegalStateException::new);

        ServiceEntity serviceEntityFromDb = optional.get();

        log.info("About to update item " + serviceEntityFromDb.toString() + " to " + serviceEntity.toString());

        serviceEntityFromDb.setCode(serviceEntity.getCode());
        serviceEntityFromDb.setName(serviceEntity.getName());
        serviceEntityFromDb.setMetric(serviceEntity.getMetric());
        serviceEntityFromDb.setType(serviceEntity.getType());
        serviceEntityFromDb.setUnitPrice(serviceEntity.getUnitPrice());

        return serviceRepository.save(serviceEntityFromDb);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<ServiceDto>) excelImportDto);
    }

    @Transactional(readOnly = true)
    public Set<String> getServiceTypes() {

        return serviceRepository.findAll().stream()
                .map(ServiceEntity::getType)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteAll() {

        serviceRepository.deleteAll();
    }

    @Transactional
    public ServiceEntity update(ServiceDto serviceDto, Long id) {

        Optional<ServiceEntity> optional = Optional.of(serviceRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        ServiceEntity serviceEntityFromDb = optional.get();

        log.info("About to update item " + serviceEntityFromDb.toString() + " to " + serviceDto.toString());

        serviceEntityFromDb.setCode(serviceDto.getCode());
        serviceEntityFromDb.setName(serviceDto.getName());
        serviceEntityFromDb.setMetric(serviceDto.getMetric());
        serviceEntityFromDb.setType(serviceDto.getType());
        serviceEntityFromDb.setUnitPrice(serviceDto.getUnitPrice());

        return serviceRepository.save(serviceEntityFromDb);
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<ServiceEntity> clones = this.serviceRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                ServiceEntity serviceEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), ServiceEntity.class);
                serviceEntity.setId(null);
                serviceEntity.setYear(previousYear + 1);
                serviceEntity.setShouldUpdateYear(false);
                return serviceEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.serviceRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.serviceRepository.countByYear(year);
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}