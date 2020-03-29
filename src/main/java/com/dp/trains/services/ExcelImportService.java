package com.dp.trains.services;

import com.dp.trains.model.dto.ExcelImportDto;

import java.util.List;

public interface ExcelImportService {

    void importFromExcel(List<? extends ExcelImportDto> excelImportDto);

    int count();

    void deleteAll();
}
