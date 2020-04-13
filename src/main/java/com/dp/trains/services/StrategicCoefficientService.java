package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.model.dto.StrategicCoefficientDto;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.repository.StrategicCoefficientRepostiory;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrategicCoefficientService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final StrategicCoefficientRepostiory strategicCoefficientRepostiory;

    @Qualifier("strategicCoefficientsMapper")
    private final DefaultDtoEntityMapperService<StrategicCoefficientDto,
            StrategicCoefficientEntity> strategicCoefficientsMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) strategicCoefficientRepostiory.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<StrategicCoefficientEntity> fetch(int offset, int limit) {

        return strategicCoefficientRepostiory.findAll();
    }

    @Transactional
    public void remove(StrategicCoefficientEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        strategicCoefficientRepostiory.delete(item);
    }

    @Transactional
    public StrategicCoefficientEntity add(StrategicCoefficientDto strategicCoefficientDto) {

        StrategicCoefficientEntity strategicCoefficientEntity =
                strategicCoefficientsMapper.mapEntity(strategicCoefficientDto);

        log.info(this.getClass().getSimpleName() + " add: " + strategicCoefficientEntity.toString());

        return strategicCoefficientRepostiory.save(strategicCoefficientEntity);
    }

    @Transactional
    public List<StrategicCoefficientEntity> add(Collection<StrategicCoefficientDto> strategicCoefficientDtos) {

        Collection<StrategicCoefficientEntity> trainTypeEntities =
                strategicCoefficientsMapper.mapEntities(strategicCoefficientDtos);

        return strategicCoefficientRepostiory.saveAll(trainTypeEntities);
    }

    @Transactional
    public StrategicCoefficientEntity update(StrategicCoefficientEntity item) {

        Optional<StrategicCoefficientEntity> optional = Optional.of(strategicCoefficientRepostiory
                .findById(item.getId())).orElseThrow(IllegalStateException::new);

        StrategicCoefficientEntity strategicCoefficientEntityFromDb = optional.get();

        log.info("About to update item " + strategicCoefficientEntityFromDb.toString() + " to " + item.toString());

        strategicCoefficientEntityFromDb.setCode(item.getCode());
        strategicCoefficientEntityFromDb.setCoefficient(item.getCoefficient());
        strategicCoefficientEntityFromDb.setName(item.getName());

        return strategicCoefficientRepostiory.save(strategicCoefficientEntityFromDb);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<StrategicCoefficientDto>) excelImportDto);
    }

    @Override
    @Transactional
    public void deleteAll() {

        strategicCoefficientRepostiory.deleteAll();
    }

    @Transactional
    public StrategicCoefficientEntity update(StrategicCoefficientDto strategicCoefficientDto, Long id) {

        Optional<StrategicCoefficientEntity> optional = Optional.of(strategicCoefficientRepostiory
                .findById(id)).orElseThrow(IllegalStateException::new);

        StrategicCoefficientEntity strategicCoefficientEntityFromDb = optional.get();

        log.info("About to update item " + strategicCoefficientEntityFromDb.toString() +
                " to " + strategicCoefficientDto.toString());

        strategicCoefficientEntityFromDb.setCode(strategicCoefficientDto.getCode());
        strategicCoefficientEntityFromDb.setCoefficient(strategicCoefficientDto.getCoefficient());
        strategicCoefficientEntityFromDb.setName(strategicCoefficientDto.getName());

        return strategicCoefficientRepostiory.save(strategicCoefficientEntityFromDb);
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<StrategicCoefficientEntity> clones = this.strategicCoefficientRepostiory
                .findAllByYear(previousYear).stream().map(x -> {
                    try {

                        StrategicCoefficientEntity strategicCoefficientEntity =
                                defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x),
                                        StrategicCoefficientEntity.class);
                        strategicCoefficientEntity.setId(null);
                        strategicCoefficientEntity.setYear(previousYear + 1);
                        strategicCoefficientEntity.setShouldUpdateYear(false);
                        return strategicCoefficientEntity;

                    } catch (JsonProcessingException e) {

                        log.error("Error deep copying:" + x.toString() + " Exception: ", e);
                    }
                    return null;
                }).collect(Collectors.toList());

        this.strategicCoefficientRepostiory.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.strategicCoefficientRepostiory.countByYear(year);
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