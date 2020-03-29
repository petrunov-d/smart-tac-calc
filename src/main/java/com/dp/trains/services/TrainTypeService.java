package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.TrainTypeDto;
import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.repository.TrainTypeRepository;
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
public class TrainTypeService implements ExcelImportService {

    private final TrainTypeRepository trainTypeRepository;

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
}
