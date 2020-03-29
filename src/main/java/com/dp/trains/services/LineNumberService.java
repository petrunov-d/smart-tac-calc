package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.LineNumberDto;
import com.dp.trains.model.entities.LineNumberEntity;
import com.dp.trains.repository.LineNumberRepository;
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
public class LineNumberService implements ExcelImportService {

    private final LineNumberRepository lineNumberRepository;

    @Qualifier("lineNumberMapper")
    private final DefaultDtoEntityMapperService<LineNumberDto, LineNumberEntity> lineNumberMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) lineNumberRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<LineNumberEntity> fetch(int offset, int limit) {

        return lineNumberRepository.findAll();
    }

    @Transactional
    public void remove(LineNumberEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        lineNumberRepository.delete(item);
    }

    @Transactional
    public LineNumberEntity add(LineNumberDto lineNumberDto) {

        LineNumberEntity lineNumberEntity = lineNumberMapper.mapEntity(lineNumberDto);

        log.info(this.getClass().getSimpleName() + " add: " + lineNumberEntity.toString());

        return lineNumberRepository.save(lineNumberEntity);
    }

    @Transactional
    public LineNumberEntity update(LineNumberEntity item) {

        Optional<LineNumberEntity> optional = Optional.of(lineNumberRepository
                .findById(item.getId())).orElseThrow(IllegalStateException::new);

        LineNumberEntity lineNumberEntityFromDB = optional.get();

        log.info("About to update item " + lineNumberEntityFromDB.toString() + " to " + item.toString());

        lineNumberEntityFromDB.setLineNumber(item.getLineNumber());
        lineNumberEntityFromDB.setDescription(item.getDescription());

        return lineNumberRepository.save(lineNumberEntityFromDB);
    }

    @Transactional
    public LineNumberEntity update(LineNumberDto lineNumberDto, Long id) {

        Optional<LineNumberEntity> optional = Optional.of(lineNumberRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        LineNumberEntity lineNumberEntityFromDB = optional.get();

        log.info("About to update item " + lineNumberEntityFromDB.toString() + " to " + lineNumberDto.toString());

        lineNumberEntityFromDB.setLineNumber(lineNumberDto.getLineNumber());
        lineNumberEntityFromDB.setDescription(lineNumberDto.getDescription());

        return lineNumberRepository.save(lineNumberEntityFromDB);
    }

    @Transactional
    public void add(Collection<LineNumberDto> lineNumberDtos) {

        Collection<LineNumberEntity> lineNumberEntities = lineNumberMapper.mapEntities(lineNumberDtos);

        lineNumberRepository.saveAll(lineNumberEntities);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<LineNumberDto>) excelImportDto);
    }

    @Transactional(readOnly = true)
    public Set<Integer> getLineNumbersAsInts() {

        return lineNumberRepository.findAll()
                .stream()
                .map(LineNumberEntity::getLineNumber)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteAll() {

        lineNumberRepository.deleteAll();
    }
}