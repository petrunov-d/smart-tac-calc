package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.MarkupCoefficientDto;
import com.dp.trains.model.entities.MarkupCoefficientEntity;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.repository.MarkupCoefficientRepository;
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
@SuppressWarnings("unchecked")
public class MarkupCoefficientService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final MarkupCoefficientRepository markupCoefficientRepository;

    @Qualifier("markupCoefficientMapper")
    private final DefaultDtoEntityMapperService<MarkupCoefficientDto, MarkupCoefficientEntity> markupCoefficientMapper;

    @Override
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<MarkupCoefficientDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) markupCoefficientRepository.count();
    }

    @Override
    @YearAgnostic
    @Transactional(readOnly = true)
    public int countByYear(int year) {

        return markupCoefficientRepository.countByYear(year);
    }

    @Override
    public void deleteAll() {

        this.markupCoefficientRepository.deleteAll();
    }

    @Override
    public String getDisplayName() {
        return this.getClass().getSimpleName();
    }

    @Override
    @YearAgnostic
    @Transactional
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {

        List<MarkupCoefficientEntity> clones = this.markupCoefficientRepository.findAllByYear(previousYear).stream().map(x -> {
            try {

                MarkupCoefficientEntity markupCoefficientEntity =
                        defaultObjectMapper.readValue(defaultObjectMapper.writeValueAsString(x), MarkupCoefficientEntity.class);
                markupCoefficientEntity.setId(null);
                markupCoefficientEntity.setYear(previousYear + 1);
                markupCoefficientEntity.setShouldUpdateYear(false);
                return markupCoefficientEntity;

            } catch (JsonProcessingException e) {

                log.error("Error deep copying:" + x.toString() + " Exception: ", e);
            }
            return null;
        }).collect(Collectors.toList());

        this.markupCoefficientRepository.saveAll(clones);

        return PreviousYearCopyingResultViewModel.builder()
                .displayName(getDisplayName())
                .copyCount(clones.size())
                .build();
    }

    @Transactional(readOnly = true)
    public Collection<MarkupCoefficientEntity> fetch(int offset, int limit) {

        return this.markupCoefficientRepository.findAll();
    }

    @Transactional
    public void remove(MarkupCoefficientEntity item) {

        log.info(this.getClass().getSimpleName() + " delete: " + item.toString());

        this.markupCoefficientRepository.delete(item);
    }

    @Transactional
    public MarkupCoefficientEntity add(MarkupCoefficientDto markupCoefficientDto) {

        MarkupCoefficientEntity carrierCompanyEntity = this.markupCoefficientMapper.mapEntity(markupCoefficientDto);

        log.info(getDisplayName() + " add: " + carrierCompanyEntity.toString());

        return this.markupCoefficientRepository.save(carrierCompanyEntity);
    }

    @Transactional
    public void add(Collection<MarkupCoefficientDto> markupCoefficientDtos) {

        Collection<MarkupCoefficientEntity> markupCoefficientEntities =
                this.markupCoefficientMapper.mapEntities(markupCoefficientDtos);

        this.markupCoefficientRepository.saveAll(markupCoefficientEntities);
    }

    @Transactional
    public MarkupCoefficientEntity update(MarkupCoefficientEntity markupCoefficientEntity) {

        Optional<MarkupCoefficientEntity> optional = Optional.of(this.markupCoefficientRepository
                .findById(markupCoefficientEntity.getId())).orElseThrow(IllegalStateException::new);

        MarkupCoefficientEntity markupCoefficientEntityFromDb = optional.get();

        log.info("About to update item " + markupCoefficientEntityFromDb.toString() +
                " to " + markupCoefficientEntity.toString());

        markupCoefficientEntityFromDb.setCode(markupCoefficientEntity.getCode());
        markupCoefficientEntityFromDb.setCoefficient(markupCoefficientEntity.getCoefficient());
        markupCoefficientEntityFromDb.setName(markupCoefficientEntity.getName());

        return this.markupCoefficientRepository.save(markupCoefficientEntityFromDb);
    }

    @Transactional
    public MarkupCoefficientEntity update(MarkupCoefficientDto carrierCompanyDto, Long id) {

        Optional<MarkupCoefficientEntity> optional = Optional.of(this.markupCoefficientRepository
                .findById(id)).orElseThrow(IllegalStateException::new);

        MarkupCoefficientEntity markupCoefficientEntityFromDb = optional.get();

        log.info("About to update item " + markupCoefficientEntityFromDb.toString() + " to " + carrierCompanyDto.toString());

        markupCoefficientEntityFromDb.setCode(carrierCompanyDto.getCode());
        markupCoefficientEntityFromDb.setName(carrierCompanyDto.getName());
        markupCoefficientEntityFromDb.setCoefficient(carrierCompanyDto.getCoefficient());

        return this.markupCoefficientRepository.save(markupCoefficientEntityFromDb);
    }
}
