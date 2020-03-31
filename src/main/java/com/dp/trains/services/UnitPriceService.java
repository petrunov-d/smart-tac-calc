package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
import com.dp.trains.model.dto.UnitPriceDto;
import com.dp.trains.model.entities.UnitPriceEntity;
import com.dp.trains.repository.UnitPriceRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "StreamToLoop", "rawtypes", "OptionalGetWithoutIsPresent"})
public class UnitPriceService implements BaseImportService {

    private final UnitPriceRepository unitPriceRepository;
    private final ObjectMapper defaultObjectMapper;

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
                                .mapToDouble(UnitPriceDto::getUnitPrice)
                                .average()
                                .getAsDouble())
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
    @Transactional(readOnly = true)
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {

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

        return PreviousYearCopyingResultDto.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
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
}