package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.UnitPriceDataIntegrity;
import com.dp.trains.model.dto.UnitPriceDto;
import com.dp.trains.model.entities.FinancialDataEntity;
import com.dp.trains.model.entities.TrafficDataEntity;
import com.dp.trains.model.entities.UnitPriceEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.repository.UnitPriceRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "StreamToLoop", "rawtypes", "OptionalGetWithoutIsPresent"})
public class UnitPriceService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final UnitPriceRepository unitPriceRepository;

    private final TrafficDataService trafficDataService;
    private final FinancialDataService financialDataService;

    @Qualifier("unitPriceMapper")
    private final DefaultDtoEntityMapperService<UnitPriceDto, UnitPriceEntity> unitPriceMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) unitPriceRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<UnitPriceEntity> fetch(int offset, int limit) {

        return unitPriceRepository.findAll();
    }

    @Transactional
    public List<UnitPriceEntity> add(Collection<UnitPriceDto> unitPriceDtos) {

        Collection<UnitPriceEntity> unitPriceEntities = unitPriceMapper.mapEntities(unitPriceDtos);

        return unitPriceRepository.saveAll(unitPriceEntities);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<UnitPriceDto>) excelImportDto);
    }

    @Override
    @Transactional
    public void deleteAll() {

        unitPriceRepository.deleteAll();
    }

    @YearAgnostic
    @Transactional(readOnly = true)
    public Collection<UnitPriceDto> calculateSinglePriceForYears(Integer yearsToGoBack, Integer currentYear) {

        if (yearsToGoBack == null || currentYear == null) {

            throw new IllegalStateException();
        }

        List<UnitPriceEntity> unitPrices = unitPriceRepository.findAllByYearBetween(
                (currentYear - 1) - (yearsToGoBack - 1), currentYear - 1);

        List<UnitPriceDto> unitPriceDtos = (List<UnitPriceDto>) unitPriceMapper.mapEntities((Collection) unitPrices);

        log.info("Calculating single price for years to go back:" + yearsToGoBack + " current year:" + currentYear);

        return unitPriceDtos
                .stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .map(unitPriceDto -> UnitPriceDto
                        .builder()
                        .code(unitPriceDto.getKey().getCode())
                        .measure(unitPriceDto.getKey().getMeasure())
                        .name(unitPriceDto.getKey().getName())
                        .unitPrice(unitPriceDto.getValue()
                                .stream()
                                .map(UnitPriceDto::getUnitPrice)
                                .collect(Collectors2.summarizingBigDecimal(e -> e))
                                .getAverage(MathContext.DECIMAL64))
                        .build())
                .collect(Collectors.toList());
    }

    @YearAgnostic
    @Transactional(readOnly = true)
    public Map<Integer, Boolean> validateUnitPriceDataIntegrityForYears(Integer yearsToGoBack, Integer currentYear) {

        Map<Integer, Boolean> validationResult = Maps.newHashMap();

        if (yearsToGoBack == null || currentYear == null) {

            throw new IllegalStateException();
        }

        int startYear = (currentYear - 1) - (yearsToGoBack - 1);
        int endYear = currentYear - 1;

        log.info("Validating data for unit prices between years: " + startYear + "<-> " + endYear);

        Map<Integer, List<UnitPriceEntity>> unitPricePerYear = unitPriceRepository
                .findAllByYearBetween(startYear, endYear)
                .stream()
                .collect(groupingBy(UnitPriceEntity::getYear));

        if (unitPricePerYear.entrySet().size() == 0) {

            for (int i = startYear; i < currentYear; i++) {

                validationResult.put(i, false);
            }
        }

        unitPricePerYear.forEach((key, forYear) -> {

            for (UnitPriceEntity unitPriceEntity : forYear) {

                if (unitPriceEntity.getUnitPrice() == null) {

                    log.info("Found entity with null unit price:" + unitPriceEntity.toString());

                    validationResult.put(key, false);
                    break;
                }
            }
        });

        log.info("Validation result: " + validationResult.toString());

        return validationResult;
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<UnitPriceEntity> clones = this.unitPriceRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                UnitPriceEntity unitPriceEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), UnitPriceEntity.class);
                unitPriceEntity.setId(null);
                unitPriceEntity.setYear(previousYear + 1);
                unitPriceEntity.setShouldUpdateYear(false);
                return unitPriceEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.unitPriceRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Transactional(readOnly = true)
    public UnitPriceEntity findByCode(String code) {

        return this.unitPriceRepository.findByCode(code);
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.unitPriceRepository.countByYear(year);
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    @Transactional(readOnly = true)
    public UnitPriceDataIntegrity getDataIntegrityState() {

        int financialDataCount = financialDataService.count();
        int trafficDataCount = trafficDataService.count();

        if (financialDataCount == 0 && trafficDataCount == 0) {

            return UnitPriceDataIntegrity.ALL_DATA_MISSING;

        } else if (financialDataService.count() == 0) {

            return UnitPriceDataIntegrity.MISSING_FINANCIAL_DATA;

        } else if (trafficDataService.count() == 0) {

            return UnitPriceDataIntegrity.MISSING_TRAFFIC_DATA;
        }

        return UnitPriceDataIntegrity.ALL_DATA_PRESENT;
    }

    @Transactional
    public void calculateSinglePriceForCurrentYear() {

        List<FinancialDataEntity> financialDataEntities = financialDataService.getAll();
        List<TrafficDataEntity> trafficDataEntities = trafficDataService.getAll();
        List<UnitPriceEntity> unitPriceEntities = Lists.newArrayList(this.fetch(0, 0));

        int financialDataEntitiesSize = financialDataEntities.size();
        int trafficDataEntitiesSize = trafficDataEntities.size();

        if ((financialDataEntitiesSize == 0 || trafficDataEntitiesSize == 0 || unitPriceEntities.size() == 0) ||
                (financialDataEntitiesSize != trafficDataEntitiesSize ||
                        unitPriceEntities.size() != financialDataEntitiesSize)) {

            throw new IllegalStateException("Data Integrity Violation -> Financial Data Size:"
                    + financialDataEntitiesSize + " Traffic Data Size: " + trafficDataEntitiesSize +
                    " Unit Price Size:" + unitPriceEntities.size());
        }

        for (int i = 0; i < financialDataEntitiesSize; i++) {

            BigDecimal financialDataDirectCostValue = financialDataEntities.get(i).getDirectCostValue();
            BigDecimal trafficDataDirectCostValue = trafficDataEntities.get(i).getDirectCostValue();

            BigDecimal result = financialDataDirectCostValue.divide(trafficDataDirectCostValue, MathContext.DECIMAL64);

            log.info("Financial Data Direct Cost Value: " + financialDataDirectCostValue.toString() +
                    " Traffic Data Direct Cost Value: " + trafficDataDirectCostValue.toString() + " Result :" +
                    result.toString());

            unitPriceEntities.get(i).setUnitPrice(result);
        }

        this.unitPriceRepository.saveAll(unitPriceEntities);
    }

    @Transactional(readOnly = true)
    public Boolean checkIfAlreadyHasDataCalculated() {

        return this.fetch(0, 0).stream()
                .filter(Objects::nonNull)
                .anyMatch(x -> x.getUnitPrice() != null);
    }
}