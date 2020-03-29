package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.ServiceDto;
import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.repository.ServiceRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceService implements ExcelImportService {

    private final ServiceRepository serviceRepository;

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

        return serviceRepository.findAll().stream().map(ServiceEntity::getType).collect(Collectors.toSet());
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
}
