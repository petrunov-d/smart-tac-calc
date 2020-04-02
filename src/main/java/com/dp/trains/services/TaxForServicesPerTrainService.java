package com.dp.trains.services;

import com.dp.trains.model.dto.*;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.model.entities.TaxForServicesPerTrainEntity;
import com.dp.trains.repository.TaxForServicesPerTrainRepository;
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
public class TaxForServicesPerTrainService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final TaxForServicesPerTrainRepository taxForServicesPerTrainRepository;

    @Qualifier("taxForServicesPerTrainMapper")
    private final DefaultDtoEntityMapperService<TaxForServicesPerTrainDto,
            TaxForServicesPerTrainEntity> taxForServicesPerTrainMapper;

    @Transactional
    public void add(Collection<TaxForServicesPerTrainDto> trainTypeDto) {

        Collection<TaxForServicesPerTrainEntity> taxForServicesPerTrainDtos =
                taxForServicesPerTrainMapper.mapEntities(trainTypeDto);

        taxForServicesPerTrainRepository.saveAll(taxForServicesPerTrainDtos);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<TaxForServicesPerTrainDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) taxForServicesPerTrainRepository.count();
    }

    @Override
    @Transactional
    public void deleteAll() {

        taxForServicesPerTrainRepository.deleteAll();
    }

    @Override
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {
        return null;
    }

    @Override
    public int countByYear(int year) {
        return -1;
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public CalculateFinalTaxPerTrainDto calculateFinalTaxForTrain(List<CalculateTaxPerTrainRowDataDto> gatherAllRowData,
                                                                  StrategicCoefficientEntity strategicCoefficientEntity,
                                                                  Integer trainNumber,
                                                                  String trainType,
                                                                  Double tonnage) {

        return CalculateFinalTaxPerTrainDto
                .builder()
                .build();
    }
}