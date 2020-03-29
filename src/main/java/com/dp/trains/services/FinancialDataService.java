package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.FinancialDataDto;
import com.dp.trains.model.entities.FinancialDataEntity;
import com.dp.trains.repository.FinancialDataRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialDataService implements ExcelImportService {

    private final FinancialDataRepository financialDataRepository;

    @Qualifier("financialDataMapper")
    private final DefaultDtoEntityMapperService<FinancialDataDto, FinancialDataEntity> financialDataMapper;

    @Transactional
    public void add(Collection<FinancialDataDto> financialDataDtos) {

        Collection<FinancialDataEntity> lineNumberEntities = financialDataMapper.mapEntities(financialDataDtos);

        financialDataRepository.saveAll(lineNumberEntities);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<FinancialDataDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) financialDataRepository.count();
    }

    @Override
    @Transactional
    public void deleteAll() {

        financialDataRepository.deleteAll();
    }
}
