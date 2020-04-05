package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.FinancialDataDto;
import com.dp.trains.model.dto.PreviousYearCopyingResultDto;
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
@SuppressWarnings("unchecked")
public class FinancialDataService implements BaseImportService {

    private final FinancialDataRepository financialDataRepository;

    @Qualifier("financialDataMapper")
    private final DefaultDtoEntityMapperService<FinancialDataDto, FinancialDataEntity> financialDataMapper;

    @Transactional
    public void add(Collection<FinancialDataDto> financialDataDtos) {

        Collection<FinancialDataEntity> lineNumberEntities = financialDataMapper.mapEntities(financialDataDtos);

        financialDataRepository.saveAll(lineNumberEntities);
    }

    @Transactional(readOnly = true)
    public FinancialDataEntity findByCode(Integer code) {

        return this.financialDataRepository.findByCode(code);
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
    public int countByYear(int year) {
        return -1;
    }

    @Override
    @Transactional
    public void deleteAll() {

        financialDataRepository.deleteAll();
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {

        return null;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
