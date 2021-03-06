package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.*;
import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.model.viewmodels.RailStationViewModel;
import com.dp.trains.repository.RailStationRepository;
import com.dp.trains.repository.SectionRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@SuppressWarnings("unchecked")
public class SectionsService implements BaseImportService {

    private static final String nonKeyStationKeyFormat = "nonkeystation%d";
    private static final String nonKeyStationKeyKilometersFormat = "kilometresfornonkeystation%dtofirstkeystation";

    private final ObjectMapper defaultObjectMapper;
    private final SectionRepository sectionsRepository;
    private final RailStationRepository railStationRepository;

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
                        .collect(Collectors.toMap(e ->
                                        StringUtils.normalizeSpace(e.getKey()).replaceAll("\\s", "").toLowerCase(),
                                Map.Entry::getValue));

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

        sectionEntities.forEach(this.sectionsRepository::saveAndFlush);
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

        sectionEntities.forEach(this.sectionsRepository::saveAndFlush);
    }

    @Transactional(readOnly = true)
    public Set<String> getFirstKeyPoints() {

        return this.sectionsRepository.findAll().stream()
                .map(SectionEntity::getFirstKeyPoint)
                .collect(Collectors.toSet());
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


    @Transactional(readOnly = true)
    public Set<SectionEntity> findAllByFirstAndLastKeyPoint(String keyPoint) {

        Set<SectionEntity> sectionEntities;

        Set<SectionEntity> allByFirstKeyPoint = this.sectionsRepository.findAllByFirstKeyPoint(keyPoint);
        Set<SectionEntity> allByLastKeyPoint = this.sectionsRepository.findAllByLastKeyPoint(keyPoint);

        sectionEntities = Sets.newHashSet(allByFirstKeyPoint);
        sectionEntities.addAll(allByLastKeyPoint);

        return sectionEntities;
    }

    @Transactional(readOnly = true)
    public SectionEntity findByNonKeyStation(String selectedStation) {

        return this.sectionsRepository.findBySubsectionNonKeyStation(selectedStation);
    }

    @Transactional(readOnly = true)
    public Collection<SectionEntity> findByFirstKeyPoint(String selectedStation) {

        return this.sectionsRepository.findAllByFirstKeyPoint(selectedStation);
    }

    @Transactional(readOnly = true)
    public Collection<SectionEntity> findByLastKeyPoint(String selectedStation) {

        return this.sectionsRepository.findAllByLastKeyPoint(selectedStation);
    }

    @Transactional(readOnly = true)
    public List<CalculateTaxPerTrainRowDataDto> findByRawDtos(List<CPPTRowDataDto> rawData) {

        List<CalculateTaxPerTrainRowDataDto> calculateTaxPerTrainRowDataDtos = Lists.newArrayList();

        for (int i = 0; i < rawData.size(); i++) {

            CPPTRowDataDto rowDataDto = rawData.get(i);
            CPPTRowDataDto nextRowDataDto = rawData.get(i == rawData.size() - 1 ? i : i + 1);
            CPPTRowDataDto previousRowDataDto = rawData.get(i == 0 ? i : i - 1);

            SectionNeighboursDto sectionNeighboursDto = findSection(previousRowDataDto, rowDataDto, nextRowDataDto, i);

            CalculateTaxPerTrainRowDataDto calculateTaxPerTrainRowDataDto = new CalculateTaxPerTrainRowDataDto();

            calculateTaxPerTrainRowDataDto.setTonnage(rowDataDto.getTonnage());
            calculateTaxPerTrainRowDataDto.setLocomotiveWeight(rowDataDto.getLocomotiveWeight());
            calculateTaxPerTrainRowDataDto.setLocomotiveSeries(rowDataDto.getLocomotiveSeries());
            calculateTaxPerTrainRowDataDto.setServiceChargesPerTrainEntityList(rowDataDto.getServiceChargesPerTrainEntityList());
            calculateTaxPerTrainRowDataDto.setSection(sectionNeighboursDto);

            calculateTaxPerTrainRowDataDtos.add(calculateTaxPerTrainRowDataDto);
        }

        return calculateTaxPerTrainRowDataDtos.stream().filter(x -> x.getSection() != null).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SectionNeighboursDto findSection(CPPTRowDataDto previousRowDataDto, CPPTRowDataDto currentRowDataDto, CPPTRowDataDto nextRowDataDto, int i) {

        SectionNeighboursDto sectionNeighboursDto = null;
        RailStationViewModel previousRailStation = previousRowDataDto.getStationViewModel();
        RailStationViewModel currentRailStation = currentRowDataDto.getStationViewModel();
        RailStationViewModel nextRailStation = nextRowDataDto.getStationViewModel();

        if (currentRowDataDto.getStationViewModel().getIsKeyStation()) {

            String firstRailStationName = currentRailStation.getRailStation();
            String nextRailStationName = nextRailStation.getRailStation();

            Set<SectionEntity> firstRailStationSectionsFirst = sectionsRepository.findAllByFirstKeyPoint(firstRailStationName);
            Set<SectionEntity> firstRailStationSectionsLast = sectionsRepository.findAllByLastKeyPoint(firstRailStationName);

            Set<SectionEntity> secondRailStationSectionsFirst = sectionsRepository.findAllByFirstKeyPoint(nextRailStationName);
            Set<SectionEntity> secondRailStationSectionsLast = sectionsRepository.findAllByLastKeyPoint(nextRailStationName);

            Set<SectionEntity> result = Sets.newHashSet(firstRailStationSectionsFirst);
            result.addAll(firstRailStationSectionsLast);
            result.addAll(secondRailStationSectionsFirst);
            result.addAll(secondRailStationSectionsLast);

            for (SectionEntity sectionEntity : result) {

                RailStationEntity railStationEntitySource = railStationRepository.findByStation(sectionEntity.getFirstKeyPoint());
                RailStationEntity railStationEntityDestination = railStationRepository.findByStation(sectionEntity.getLastKeyPoint());

                RailStationEntity currentRailStationEntitySource;
                RailStationEntity currentRailStationEntityDestination;

                if (!(firstRailStationName.equals(nextRailStationName))) {

                    if ((sectionEntity.getFirstKeyPoint().equals(firstRailStationName) || sectionEntity.getLastKeyPoint().equals(firstRailStationName)) &&
                            (sectionEntity.getFirstKeyPoint().equals(nextRailStationName) || sectionEntity.getLastKeyPoint().equals(nextRailStationName))) {

                        log.info("FirstRailStation: " + firstRailStationName + " SecondRailStation: " + nextRailStationName);

                        if (firstRailStationName.equals(railStationEntitySource.getStation())) {

                            currentRailStationEntitySource = railStationEntitySource;
                            currentRailStationEntityDestination = railStationEntityDestination;

                        } else {

                            currentRailStationEntitySource = railStationEntityDestination;
                            currentRailStationEntityDestination = railStationEntitySource;
                        }

                        return SectionNeighboursDto
                                .builder()
                                .originalSource(railStationEntitySource)
                                .originalDestination(railStationEntityDestination)
                                .currentSource(currentRailStationEntitySource)
                                .currentDestination(currentRailStationEntityDestination)
                                .isElectrified(sectionEntity.getIsElectrified())
                                .isKeyStation(true)
                                .kilometersBetweenStations(sectionEntity.getKilometersBetweenStations())
                                .lineNumber(sectionEntity.getLineNumber())
                                .typeOfLine(sectionEntity.getLineType())
                                .rowIndex(i + 1)
                                .build();
                    }
                }
            }

        } else if (!currentRowDataDto.getStationViewModel().getIsKeyStation()) {

            SectionEntity sectionEntity = this.sectionsRepository.findBySubsectionNonKeyStation(currentRailStation.getRailStation());

            log.info("rail station was non key.");

            RailStationEntity railStationEntitySource = railStationRepository.findByStation(sectionEntity.getFirstKeyPoint());
            RailStationEntity railStationEntityDestination = railStationRepository.findByStation(sectionEntity.getLastKeyPoint());
            RailStationEntity nonKeyStation = railStationRepository.findByStation(currentRowDataDto.getStationViewModel().getRailStation());

            RailStationEntity currentRailStationEntitySource = null;
            RailStationEntity currentRailStationEntityDestination = null;
            Double kilometersBetweenStations = null;

            if (currentRailStation.getRailStation().equals(railStationEntitySource.getStation())) {

                currentRailStationEntitySource = railStationEntitySource;
                currentRailStationEntityDestination = railStationEntityDestination;
                kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), true);

            } else if (currentRailStation.getRailStation().equals(railStationEntityDestination.getStation())) {

                currentRailStationEntitySource = railStationEntityDestination;
                currentRailStationEntityDestination = railStationEntitySource;
                kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), false);

            } else if (!currentRailStation.getRailStation().equals(railStationEntitySource.getStation()) &&
                    !currentRailStation.getRailStation().equals(railStationEntityDestination.getStation())) {

                if (i == 0) {

                    currentRailStationEntitySource = railStationRepository.findByStation(currentRailStation.getRailStation());

                    if (nextRailStation.getRailStation().equals(sectionEntity.getFirstKeyPoint())) {

                        currentRailStationEntityDestination = railStationEntitySource;
                        kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), true);

                    } else if (nextRailStation.getRailStation().equals(sectionEntity.getLastKeyPoint())) {

                        currentRailStationEntityDestination = railStationEntityDestination;
                        kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), false);
                    }

                } else {

                    currentRailStationEntityDestination = railStationRepository.findByStation(currentRailStation.getRailStation());

                    if (previousRailStation.getRailStation().equals(sectionEntity.getFirstKeyPoint())) {

                        currentRailStationEntitySource = railStationEntitySource;
                        kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), true);

                    } else if (previousRailStation.getRailStation().equals(sectionEntity.getLastKeyPoint())) {

                        currentRailStationEntitySource = railStationEntityDestination;
                        kilometersBetweenStations = findPartialKilometers(sectionEntity, currentRowDataDto.getStationViewModel().getRailStation(), false);
                    }
                }
            }

            return SectionNeighboursDto
                    .builder()
                    .originalSource(railStationEntitySource)
                    .originalDestination(railStationEntityDestination)
                    .isElectrified(sectionEntity.getIsElectrified())
                    .currentSource(currentRailStationEntitySource)
                    .currentDestination(currentRailStationEntityDestination)
                    .nonKeyStation(nonKeyStation)
                    .isKeyStation(false)
                    .kilometersBetweenStations(kilometersBetweenStations)
                    .lineNumber(sectionEntity.getLineNumber())
                    .typeOfLine(sectionEntity.getLineType())
                    .rowIndex(i + 1)
                    .build();
        }

        return sectionNeighboursDto;
    }

    private Double findPartialKilometers(SectionEntity sectionEntity, String targetStation, boolean isStationOrderPreserved) {

        for (SubSectionEntity subSectionEntity : sectionEntity.getSubSectionEntities()) {

            if (subSectionEntity.getNonKeyStation().equals(targetStation) && isStationOrderPreserved) {

                return subSectionEntity.getKilometers();

            } else if (subSectionEntity.getNonKeyStation().equals(targetStation) && !isStationOrderPreserved) {

                return sectionEntity.getKilometersBetweenStations() - subSectionEntity.getKilometers();
            }
        }

        return -1.0;
    }
}