package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.model.dto.TrainTypeDto;
import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.repository.TrainTypeRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainTypeService implements BaseImportService {

    private final TrainTypeRepository trainTypeRepository;
    private final ObjectMapper defaultObjectMapper;

    @Qualifier("trainTypeMapper")
    private final DefaultDtoEntityMapperService<TrainTypeDto, TrainTypeEntity> trainTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) trainTypeRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<TrainTypeEntity> fetch(int offset, int limit) {

        return trainTypeRepository.findAll();
    }

    @Transactional
    public void remove(TrainTypeEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        this.trainTypeRepository.delete(item);
    }

    @Transactional
    public TrainTypeEntity add(TrainTypeDto trainTypeDto) {

        TrainTypeEntity trainTypeEntity = trainTypeMapper.mapEntity(trainTypeDto);

        log.info(this.getClass().getSimpleName() + " add: " + trainTypeEntity.toString());

        return trainTypeRepository.save(trainTypeEntity);
    }

    @Transactional
    public void add(Collection<TrainTypeDto> trainTypeDto) {

        Collection<TrainTypeEntity> trainTypeEntities = trainTypeMapper.mapEntities(trainTypeDto);

        trainTypeRepository.saveAll(trainTypeEntities);
    }

    @Transactional
    public TrainTypeEntity update(TrainTypeEntity trainTypeEntity) {

        Optional<TrainTypeEntity> optional = Optional.of(trainTypeRepository
                .findById(trainTypeEntity.getId())).orElseThrow(IllegalStateException::new);

        TrainTypeEntity trainTypeEntityFromDb = optional.get();

        log.info("About to update item " + trainTypeEntityFromDb.toString() + " to " + trainTypeEntity.toString());

        trainTypeEntityFromDb.setCode(trainTypeEntity.getCode());
        trainTypeEntityFromDb.setName(trainTypeEntity.getName());

        return trainTypeRepository.save(trainTypeEntityFromDb);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDtos) {

        this.add((Collection<TrainTypeDto>) excelImportDtos);
    }

    @Transactional(readOnly = true)
    public Set<String> getTrainTypes() {

        return this.trainTypeRepository.findAll().stream().map(TrainTypeEntity::getName).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteAll() {

        trainTypeRepository.deleteAll();
    }

    @Transactional
    public TrainTypeEntity update(TrainTypeDto trainTypeDto, Long id) {

        Optional<TrainTypeEntity> optional = Optional.of(trainTypeRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        TrainTypeEntity trainTypeEntityFromDb = optional.get();

        log.info("About to update item " + trainTypeEntityFromDb.toString() + " to " + trainTypeDto.toString());

        trainTypeEntityFromDb.setCode(trainTypeDto.getCode());
        trainTypeEntityFromDb.setName(trainTypeDto.getName());

        return trainTypeRepository.save(trainTypeEntityFromDb);
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<TrainTypeEntity> clones = this.trainTypeRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                TrainTypeEntity trainTypeEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), TrainTypeEntity.class);
                trainTypeEntity.setId(null);
                trainTypeEntity.setYear(previousYear + 1);
                trainTypeEntity.setShouldUpdateYear(false);
                return trainTypeEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.trainTypeRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.trainTypeRepository.countByYear(year);
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