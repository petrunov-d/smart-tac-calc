package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.LineTypeDto;
import com.dp.trains.model.entities.LineTypeEntity;
import com.dp.trains.repository.LineTypeRepository;
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
public class LineTypeService implements ExcelImportService {

    private final LineTypeRepository lineTypeRepository;

    @Qualifier("lineTypeMapper")
    private final DefaultDtoEntityMapperService<LineTypeDto, LineTypeEntity> lineTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) lineTypeRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<LineTypeEntity> fetch(int offset, int limit) {

        return lineTypeRepository.findAll();
    }

    @Transactional
    public void remove(LineTypeEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        lineTypeRepository.delete(item);
    }

    @Transactional
    public LineTypeEntity add(LineTypeDto lineTypeDto) {

        LineTypeEntity lineTypeEntity = lineTypeMapper.mapEntity(lineTypeDto);

        log.info(this.getClass().getSimpleName() + " add: " + lineTypeEntity.toString());

        return lineTypeRepository.save(lineTypeEntity);
    }

    @Transactional
    public LineTypeEntity update(LineTypeEntity item) {

        Optional<LineTypeEntity> optional = Optional.of(lineTypeRepository
                .findById(item.getId())).orElseThrow(IllegalStateException::new);

        LineTypeEntity trainTypeEntityFromDb = optional.get();

        log.info("About to update item " + trainTypeEntityFromDb.toString() + " to " + item.toString());

        trainTypeEntityFromDb.setLineType(item.getLineType());
        trainTypeEntityFromDb.setName(item.getName());

        return lineTypeRepository.save(trainTypeEntityFromDb);
    }

    @Transactional
    public void add(Collection<LineTypeDto> lineTypeDtos) {

        Collection<LineTypeEntity> lineTypeEntities = lineTypeMapper.mapEntities(lineTypeDtos);

        lineTypeRepository.saveAll(lineTypeEntities);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<LineTypeDto>) excelImportDto);
    }

    @Transactional(readOnly = true)
    public Set<String> getLineTypes() {

        return this.lineTypeRepository.findAll().stream().map(LineTypeEntity::getLineType).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteAll() {

        lineTypeRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public List<String> getLineTypeStrings() {

        return this.lineTypeRepository.findAll().stream().map(LineTypeEntity::getLineType).collect(Collectors.toList());
    }

    @Transactional
    public LineTypeEntity update(LineTypeDto lineTypeDto, Long id) {

        Optional<LineTypeEntity> optional = Optional.of(lineTypeRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        LineTypeEntity lineTypeEntityFromDb = optional.get();

        log.info("About to update item " + lineTypeEntityFromDb.toString() + " to " + lineTypeDto.toString());

        lineTypeEntityFromDb.setLineType(lineTypeDto.getLineType());
        lineTypeEntityFromDb.setName(lineTypeDto.getName());

        return lineTypeRepository.save(lineTypeEntityFromDb);
    }
}
