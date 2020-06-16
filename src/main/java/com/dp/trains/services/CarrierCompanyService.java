package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.CarrierCompanyDto;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.entities.CarrierCompanyEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.repository.CarrierCompanyRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarrierCompanyService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final CarrierCompanyRepository carrierCompanyRepository;

    @Qualifier("carrierCompanyMapper")
    private final DefaultDtoEntityMapperService<CarrierCompanyDto, CarrierCompanyEntity> carrierCompanyMapper;

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<CarrierCompanyDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) carrierCompanyRepository.count();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return this.carrierCompanyRepository.countByYear(year);
    }

    @Override
    @Transactional
    public void deleteAll() {

        this.carrierCompanyRepository.deleteAll();
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    @Transactional
    @YearAgnostic
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<CarrierCompanyEntity> clones = this.carrierCompanyRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                CarrierCompanyEntity carrierCompanyEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), CarrierCompanyEntity.class);
                carrierCompanyEntity.setId(null);
                carrierCompanyEntity.setYear(previousYear + 1);
                carrierCompanyEntity.setShouldUpdateYear(false);
                return carrierCompanyEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.carrierCompanyRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Transactional(readOnly = true)
    public Collection<CarrierCompanyEntity> fetch(int offset, int limit) {

        return this.carrierCompanyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<String> fetchNames() {

        return this.carrierCompanyRepository.findAll().stream().map(CarrierCompanyEntity::getCarrierName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional
    public void remove(CarrierCompanyEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        this.carrierCompanyRepository.delete(item);
    }

    @Transactional
    public CarrierCompanyEntity add(CarrierCompanyDto carrierCompanyDto) {

        CarrierCompanyEntity carrierCompanyEntity = carrierCompanyMapper.mapEntity(carrierCompanyDto);

        log.info(getDisplayName() + " add: " + carrierCompanyEntity.toString());

        return this.carrierCompanyRepository.save(carrierCompanyEntity);
    }

    @Transactional
    public void add(Collection<CarrierCompanyDto> carrierCompanyDtos) {

        Collection<CarrierCompanyEntity> trainTypeEntities = carrierCompanyMapper.mapEntities(carrierCompanyDtos);

        this.carrierCompanyRepository.saveAll(trainTypeEntities);
    }

    @Transactional
    public CarrierCompanyEntity update(CarrierCompanyEntity carrierCompanyEntity) {

        Optional<CarrierCompanyEntity> optional = Optional.of(this.carrierCompanyRepository
                .findById(carrierCompanyEntity.getId())).orElseThrow(IllegalStateException::new);

        CarrierCompanyEntity carrierCompanyEntityFromDb = optional.get();

        log.info("About to update item " + carrierCompanyEntityFromDb.toString() + " to " + carrierCompanyEntity.toString());

        carrierCompanyEntityFromDb.setCarrierName(carrierCompanyEntity.getCarrierName());
        carrierCompanyEntityFromDb.setLocomotiveSeries(carrierCompanyEntity.getLocomotiveSeries());
        carrierCompanyEntityFromDb.setLocomotiveType(carrierCompanyEntity.getLocomotiveType());
        carrierCompanyEntityFromDb.setLocomotiveWeight(carrierCompanyEntity.getLocomotiveWeight());

        return this.carrierCompanyRepository.save(carrierCompanyEntityFromDb);
    }

    @Transactional
    public CarrierCompanyEntity update(CarrierCompanyDto carrierCompanyDto, Long id) {

        Optional<CarrierCompanyEntity> optional = Optional.of(this.carrierCompanyRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        CarrierCompanyEntity carrierCompanyEntityFromDb = optional.get();

        log.info("About to update item " + carrierCompanyEntityFromDb.toString() + " to " + carrierCompanyDto.toString());

        carrierCompanyEntityFromDb.setCarrierName(carrierCompanyDto.getCarrierName());
        carrierCompanyEntityFromDb.setLocomotiveSeries(carrierCompanyDto.getLocomotiveSeries());
        carrierCompanyEntityFromDb.setLocomotiveType(carrierCompanyDto.getLocomotiveType());
        carrierCompanyEntityFromDb.setLocomotiveWeight(carrierCompanyDto.getLocomotiveWeight());

        return this.carrierCompanyRepository.save(carrierCompanyEntityFromDb);
    }

    @Transactional(readOnly = true)
    public Collection<LocomotiveSeriesDto> getByCarrierName(String carrierName) {

        return this.carrierCompanyRepository.findAllByCarrierName(carrierName)
                .stream()
                .map(x -> LocomotiveSeriesDto
                        .builder()
                        .series(StringUtils.isBlank(x.getLocomotiveSeries()) ? "N/A" : x.getLocomotiveSeries())
                        .weight(x.getLocomotiveWeight())
                        .build())
                .collect(Collectors.toSet());
    }
}