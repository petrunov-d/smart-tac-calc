package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
import com.dp.trains.model.dto.TrafficDataDto;
import com.dp.trains.model.entities.TrafficDataEntity;
import com.dp.trains.repository.TrafficDataRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficDataService implements BaseImportService {

    private final TrafficDataRepository trafficDataRepository;
    private final ObjectMapper defaultObjectMapper;

    @Qualifier("trafficDataMapper")
    private final DefaultDtoEntityMapperService<TrafficDataDto, TrafficDataEntity> trafficDataMapper;

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

    @Override
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {
        return null;
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.trafficDataRepository.countByYear(year);
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
