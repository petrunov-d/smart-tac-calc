package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.TrafficDataDto;
import com.dp.trains.model.entities.TrafficDataEntity;
import com.dp.trains.repository.TrafficDataRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficDataService implements ExcelImportService {

    @Qualifier("trafficDataMapper")
    private final DefaultDtoEntityMapperService<TrafficDataDto, TrafficDataEntity> trafficDataMapper;

    private final TrafficDataRepository trafficDataRepository;

    @Transactional
    public void add(Collection<TrafficDataDto> trafficDataDtos) {

        Collection<TrafficDataEntity> trafficDataEntities = trafficDataMapper.mapEntities(trafficDataDtos);

        trafficDataRepository.saveAll(trafficDataEntities);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<TrafficDataDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {
        return (int) trafficDataRepository.count();
    }

    @Override
    @Transactional
    public void deleteAll() {

        trafficDataRepository.deleteAll();
    }
}
