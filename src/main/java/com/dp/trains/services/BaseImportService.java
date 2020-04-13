package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;

import java.util.List;

public interface BaseImportService {

    void importFromExcel(List<? extends ExcelImportDto> excelImportDto);

    int count();

    int countByYear(int year);

    void deleteAll();

    String getDisplayName();

    PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear);
}
