package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.LineTypeDto;
import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
import com.dp.trains.model.entities.LineTypeEntity;
import com.dp.trains.repository.LineTypeRepository;
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
public class LineTypeService implements BaseImportService {

    private final LineTypeRepository lineTypeRepository;
    private final ObjectMapper defaultObjectMapper;

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

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {

        List<LineTypeEntity> clones = this.lineTypeRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                LineTypeEntity lineTypeEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), LineTypeEntity.class);
                lineTypeEntity.setId(null);
                lineTypeEntity.setYear(previousYear + 1);
                lineTypeEntity.setShouldUpdateYear(false);
                return lineTypeEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.lineTypeRepository.saveAll(clones);

        return PreviousYearCopyingResultDto.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.lineTypeRepository.countByYear(year);
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }
}
