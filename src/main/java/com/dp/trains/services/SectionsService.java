package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.*;
import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.model.viewmodels.StationViewModel;
import com.dp.trains.repository.SectionRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectionsService implements BaseImportService {

    private static final String nonKeyStationKeyFormat = "Nonkey station %d";
    private static final String nonKeyStationKeyKilometersFormat = "Kilometres for nonkey station %d to first key station";

    private final ObjectMapper defaultObjectMapper;
    private final SectionRepository sectionsRepository;
    private final RailStationService railStationService;

    @Qualifier("subSectionMapper")
    private final DefaultDtoEntityMapperService<SubSectionDto, SubSectionEntity> subSectionMapper;

    @Qualifier("sectionMapper")
    private final DefaultDtoEntityMapperService<SectionsDto, SectionEntity> sectionMapper;

    @Override
    @Transactional(readOnly = true)
    public int count() {

        int count = (int) sectionsRepository.count();

        log.info(this.getClass().getSimpleName() + " count: " + count);

        return count;
    }

    @Transactional(readOnly = true)
    public Collection<SectionEntity> fetch(int offset, int limit) {

        return sectionsRepository.findAll();
    }

    @Transactional
    public void remove(SectionEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        sectionsRepository.delete(item);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        List<SectionEntity> sectionEntities = Lists.newArrayList();

        List<RawExcelSectionDto> rawExcelDtos = (List<RawExcelSectionDto>) excelImportDto;

        rawExcelDtos.forEach(rawExcelSectionDto -> {

            Collection<SubSectionEntity> subSectionEntities = Lists.newArrayList();

            log.info("importing:" + rawExcelSectionDto.toString());

            SectionsDto sectionsDto = SectionsDto.builder()
                    .firstKeyPoint(rawExcelSectionDto.getFirstKeyPoint())
                    .isElectrified(rawExcelSectionDto.getIsElectrified() == null ? null :
                            rawExcelSectionDto.getIsElectrified().equals("1"))
                    .kilometersBetweenStations(rawExcelSectionDto.getKilometersBetweenStations())
                    .lastKeyPoint(rawExcelSectionDto.getLastKeyPoint())
                    .lineNumber(rawExcelSectionDto.getLineNumber())
                    .lineType(rawExcelSectionDto.getLineType())
                    .unitPrice(rawExcelSectionDto.getUnitPrice())
                    .build();

            SectionEntity sectionEntity = sectionMapper.mapEntity(sectionsDto);

            if (rawExcelSectionDto.getUnknownCells() != null) {

                Map<String, String> normalizedMap = rawExcelSectionDto.getUnknownCells()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> StringUtils.normalizeSpace(e.getKey()), Map.Entry::getValue));

                List<SubSectionDto> subSectionDtos = Lists.newArrayList();

                for (int i = 0; i < rawExcelSectionDto.getUnknownCells().size() / 2; i++) {

                    SubSectionDto subsectionsDto = SubSectionDto
                            .builder()
                            .nonKeyStation(normalizedMap.get(String.format(nonKeyStationKeyFormat, i + 1)))
                            .kilometers(Double.valueOf(normalizedMap.get(String.format(nonKeyStationKeyKilometersFormat, i + 1))))
                            .build();

                    subSectionDtos.add(subsectionsDto);
                }

                subSectionEntities = subSectionMapper.mapEntities(subSectionDtos);
                subSectionEntities.forEach(e -> e.setSectionEntity(sectionEntity));
            }


            sectionEntity.setSubSectionEntities(Lists.newArrayList(subSectionEntities));

            sectionEntities.add(sectionEntity);
        });

        this.add(sectionEntities);
    }

    @Transactional
    public SectionEntity add(SectionsDto sectionsDto) {

        SectionEntity sectionEntity = sectionMapper.mapEntity(sectionsDto);

        log.info(this.getClass().getSimpleName() + " add: " + sectionEntity.toString());

        return sectionsRepository.save(sectionEntity);
    }

    @Transactional
    public SectionEntity update(SectionEntity sectionEntity) {

        Optional<SectionEntity> optional = Optional.of(sectionsRepository
                .findById(sectionEntity.getId())).orElseThrow(IllegalStateException::new);

        SectionEntity sectionEntityFromDb = optional.get();

        log.info("About to update item " + sectionEntityFromDb.toString() + " to " + sectionEntity.toString());

        sectionEntityFromDb.setFirstKeyPoint(sectionEntity.getFirstKeyPoint());
        sectionEntityFromDb.setIsElectrified(sectionEntity.getIsElectrified());
        sectionEntityFromDb.setKilometersBetweenStations(sectionEntity.getKilometersBetweenStations());
        sectionEntityFromDb.setLastKeyPoint(sectionEntity.getLastKeyPoint());
        sectionEntityFromDb.setLineNumber(sectionEntity.getLineNumber());
        sectionEntityFromDb.setLineType(sectionEntity.getLineType());
        sectionEntityFromDb.setUnitPrice(sectionEntity.getUnitPrice());

        return sectionsRepository.save(sectionEntityFromDb);
    }

    @Transactional
    public void add(Collection<SectionEntity> sectionEntities) {

        sectionEntities.forEach(this.sectionsRepository::save);
    }

    @Transactional(readOnly = true)
    public Set<String> getFirstKeyPoints() {

        return this.sectionsRepository.findAll().stream()
                .map(SectionEntity::getFirstKeyPoint)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SectionNeighboursDto> getDirectNeighboursForSource(StationViewModel source, boolean isFirst) {

        if (source != null && !source.getIsKeyStation()) {

            return this.getDirectNeighboursForNonKeyStation(source.getSelectedStation());
        }

        Set<SectionEntity> entities = source == null ? Sets.newHashSet(this.sectionsRepository.findAll()) :
                this.sectionsRepository.findAllByFirstKeyPoint(source.getSelectedStation());

        Set<SectionNeighboursDto> sectionNeighbourDtos = Sets.newHashSet();

        for (SectionEntity sectionEntity : entities) {

            SectionNeighboursDto rootDto = getSectionNeighboursDtoFromSectionEntity(sectionEntity, true);

            sectionNeighbourDtos.add(rootDto);

            if (isFirst) {

                RailStationEntity first = railStationService.getByRailStationName(sectionEntity.getFirstKeyPoint());

                SectionNeighboursDto firstDto = SectionNeighboursDto.builder()
                        .isElectrified(sectionEntity.getIsElectrified())
                        .lineNumber(sectionEntity.getLineNumber())
                        .typeOfLine(sectionEntity.getLineType())
                        .unitPrice(sectionEntity.getUnitPrice())
                        .source(first)
                        .destination(first)
                        .kilometersBetweenStations(sectionEntity.getKilometersBetweenStations())
                        .isKeyStation(true)
                        .build();

                sectionNeighbourDtos.add(firstDto);
            }

            getSubSectionNeighbours(sectionNeighbourDtos, sectionEntity);
        }

        return sectionNeighbourDtos;
    }

    private Set<SectionNeighboursDto> getDirectNeighboursForNonKeyStation(String selectedStation) {

        SectionEntity sectionEntity = this.sectionsRepository.findBySubsectionEntity(selectedStation);
        return Sets.newHashSet(getSectionNeighboursDtoFromSectionEntity(sectionEntity, true));
    }

    private void getSubSectionNeighbours(Set<SectionNeighboursDto> sectionNeighbourDtos, SectionEntity sectionEntity) {

        for (SubSectionEntity subSectionEntity : sectionEntity.getSubSectionEntities()) {

            SectionNeighboursDto sectionNeighboursDto = getSectionNeighboursDtoFromSectionEntity(sectionEntity, false);

            Double kilometersBetweenStations = sectionEntity.getKilometersBetweenStations() - subSectionEntity.getKilometers();
            sectionNeighboursDto.setKilometersBetweenStations(kilometersBetweenStations);
            sectionNeighboursDto.setNonKeyStation(railStationService.getByRailStationName(subSectionEntity.getNonKeyStation()));

            log.info("Adding non key station:" + sectionNeighboursDto.toString());

            sectionNeighbourDtos.add(sectionNeighboursDto);
        }
    }

    private SectionNeighboursDto getSectionNeighboursDtoFromSectionEntity(SectionEntity sectionEntity, boolean isKeyStation) {

        return SectionNeighboursDto.builder()
                .isElectrified(sectionEntity.getIsElectrified())
                .lineNumber(sectionEntity.getLineNumber())
                .typeOfLine(sectionEntity.getLineType())
                .unitPrice(sectionEntity.getUnitPrice())
                .source(railStationService.getByRailStationName(sectionEntity.getFirstKeyPoint()))
                .destination(railStationService.getByRailStationName(sectionEntity.getLastKeyPoint()))
                .kilometersBetweenStations(sectionEntity.getKilometersBetweenStations())
                .isKeyStation(isKeyStation)
                .build();
    }

    @Override
    @Transactional
    public void deleteAll() {

        this.sectionsRepository.deleteAll();
    }

    @Transactional
    public SectionEntity update(SectionsDto sectionsDto, Long id) {

        Optional<SectionEntity> optional = Optional.of(sectionsRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        SectionEntity sectionEntityFromDb = optional.get();

        log.info("About to update item " + sectionEntityFromDb.toString() + " to " + sectionsDto.toString());

        sectionEntityFromDb.setFirstKeyPoint(sectionsDto.getFirstKeyPoint());
        sectionEntityFromDb.setIsElectrified(sectionsDto.getIsElectrified());
        sectionEntityFromDb.setKilometersBetweenStations(sectionsDto.getKilometersBetweenStations());
        sectionEntityFromDb.setLastKeyPoint(sectionsDto.getLastKeyPoint());
        sectionEntityFromDb.setLineNumber(sectionsDto.getLineNumber());
        sectionEntityFromDb.setLineType(sectionsDto.getLineType());
        sectionEntityFromDb.setUnitPrice(sectionsDto.getUnitPrice());

        return sectionsRepository.save(sectionEntityFromDb);
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<SectionEntity> clones = this.sectionsRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                SectionEntity sectionEntity = defaultObjectMapper
                        .readValue(defaultObjectMapper.writeValueAsString(x), SectionEntity.class);
                sectionEntity.setId(null);
                sectionEntity.setYear(previousYear + 1);
                sectionEntity.setShouldUpdateYear(false);
                return sectionEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.sectionsRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder().displayName(getDisplayName()).copyCount(clones.size())
                .build();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.sectionsRepository.countByYear(year);
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public Set<SectionNeighboursDto> getVisibleSectionNeighbourDtos(int rowIndex, boolean isFinalRow,
                                                                    Collection<SectionNeighboursDto> neighbours) {
        if (rowIndex == 1 || isFinalRow) {

            return Sets.newHashSet(neighbours);
        }

        return neighbours.stream()
                .filter(SectionNeighboursDto::getIsKeyStation)
                .collect(Collectors.toSet());
    }

    public SectionNeighboursDto getByStationAndLineNumber(SectionNeighboursDto value,
                                                          Collection<SectionNeighboursDto> neighbours) {

        Optional<SectionNeighboursDto> optionalSectionNeighboursDto = neighbours.stream()
                .filter(x -> x.equals(value))
                .findFirst();

        if (!optionalSectionNeighboursDto.isPresent()) {

            throw new IllegalStateException("Could not find station for target:" + value
                    + " neighbours " + Joiner.on(",").join(neighbours));
        }

        return neighbours.stream()
                .filter(x -> x.equals(value))
                .findFirst().get();
    }

    public Set<Integer> getLineNumbersFromSectionNeighbourDtos(Collection<SectionNeighboursDto> sectionNeighboursDtos) {

        return sectionNeighboursDtos.stream()
                .map(SectionNeighboursDto::getLineNumber)
                .collect(Collectors.toSet());
    }
}