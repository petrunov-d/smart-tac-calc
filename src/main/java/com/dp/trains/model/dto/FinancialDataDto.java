package com.dp.trains.model.dto;

import com.dp.trains.model.entities.FinancialDataEntity;
import com.googlecode.jmapper.annotations.JMap;
import com.poiji.annotation.ExcelCell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialDataDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "directCostDescrption", classes = FinancialDataEntity.class)
    private String directCostDescrption;

    @ExcelCell(1)
    @JMap(value = "directCostValue", classes = FinancialDataEntity.class)
    private BigDecimal directCostValue;

    @ExcelCell(2)
    @JMap(value = "code", classes = FinancialDataEntity.class)
    private String code;
}
