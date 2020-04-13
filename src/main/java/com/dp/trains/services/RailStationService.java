package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.model.dto.RailStationDto;
import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.repository.RailStationRepository;
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
public class RailStationService implements BaseImportService {

    private final RailStationRepository railStationRepository;
    private final ObjectMapper defaultObjectMapper;

    @Qualifier("railStationMapper")
    private final DefaultDtoEntityMapperService<RailStationDto, RailStationEntity> railStationMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) railStationRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<RailStationEntity> fetch(int offset, int limit) {

        return railStationRepository.findAll();
    }

    @Transactional
    public void remove(RailStationEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        railStationRepository.delete(item);
    }

    @Transactional
    public RailStationEntity add(RailStationDto railStationDto) {

        RailStationEntity railStationEntity = railStationMapper.mapEntity(railStationDto);

        log.info(this.getClass().getSimpleName() + " add: " + railStationEntity.toString());

        return railStationRepository.save(railStationEntity);
    }

    @Transactional
    public void add(Collection<RailStationDto> railStationDtos) {

        Collection<RailStationEntity> railStationEntities = railStationMapper.mapEntities(railStationDtos);

        railStationRepository.saveAll(railStationEntities);
    }

    @Transactional
    public RailStationEntity update(RailStationEntity railStationEntity) {

        Optional<RailStationEntity> optional = Optional.of(railStationRepository
                .findById(railStationEntity.getId())).orElseThrow(IllegalStateException::new);

        RailStationEntity railStationEntityFromDb = optional.get();

        log.info("About to update item " + railStationEntityFromDb.toString() + " to " + railStationEntity.toString());

        railStationEntityFromDb.setIsKeyStation(railStationEntity.getIsKeyStation());
        railStationEntityFromDb.setLineNumber(railStationEntity.getLineNumber());
        railStationEntityFromDb.setStation(railStationEntity.getStation());
        railStationEntityFromDb.setType(railStationEntity.getType());
        railStationEntityFromDb.setCountry(railStationEntity.getCountry());

        return railStationRepository.save(railStationEntityFromDb);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<RailStationDto>) excelImportDto);
    }

    @Override
    @Transactional
    public void deleteAll() {

        railStationRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public RailStationEntity getByRailStationName(String station) {

        return this.railStationRepository.findByStation(station);
    }

    @Transactional
    public RailStationEntity update(RailStationDto railStationDto, Long id) {

        Optional<RailStationEntity> optional = Optional.of(railStationRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        RailStationEntity railstationEntityFromDb = optional.get();

        log.info("About to update item " + railstationEntityFromDb.toString() + " to " + railStationDto.toString());

        railstationEntityFromDb.setIsKeyStation(railStationDto.getIsKeyStation());
        railstationEntityFromDb.setLineNumber(railStationDto.getLineNumber());
        railstationEntityFromDb.setStation(railStationDto.getStation());
        railstationEntityFromDb.setType(railStationDto.getType());
        railstationEntityFromDb.setCountry(railStationDto.getCountry());

        return railStationRepository.save(railstationEntityFromDb);
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<RailStationEntity> clones = this.railStationRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                RailStationEntity railStationEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), RailStationEntity.class);
                railStationEntity.setId(null);
                railStationEntity.setYear(previousYear + 1);
                railStationEntity.setShouldUpdateYear(false);
                return railStationEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.railStationRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.railStationRepository.countByYear(year);
    }

    @Transactional
    public Set<String> getAllStationNames(boolean onlyKeyStations) {

        List<RailStationEntity> entities = onlyKeyStations ? railStationRepository.findByIsKeyStationTrue() :
                railStationRepository.findByIsKeyStationFalse();

        return entities.stream().map(RailStationEntity::getStation).collect(Collectors.toSet());
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
