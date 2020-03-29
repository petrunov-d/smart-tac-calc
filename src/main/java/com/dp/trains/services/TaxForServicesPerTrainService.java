package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.TaxForServicesPerTrainDto;
import com.dp.trains.model.entities.TaxForServicesPerTrainEntity;
import com.dp.trains.repository.TaxForServicesPerTrainRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxForServicesPerTrainService implements ExcelImportService {

    @Qualifier("taxForServicesPerTrainMapper")
    private final DefaultDtoEntityMapperService<TaxForServicesPerTrainDto,
            TaxForServicesPerTrainEntity> taxForServicesPerTrainMapper;

    private final TaxForServicesPerTrainRepository taxForServicesPerTrainRepository;

    @Transactional
    public void add(Collection<TaxForServicesPerTrainDto> trainTypeDto) {

        Collection<TaxForServicesPerTrainEntity> taxForServicesPerTrainDtos =
                taxForServicesPerTrainMapper.mapEntities(trainTypeDto);

        taxForServicesPerTrainRepository.saveAll(taxForServicesPerTrainDtos);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<TaxForServicesPerTrainDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) taxForServicesPerTrainRepository.count();
    }

    @Override
    @Transactional
    public void deleteAll() {

        taxForServicesPerTrainRepository.deleteAll();
    }
}