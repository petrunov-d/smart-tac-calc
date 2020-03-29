package com.dp.trains.model.dto;

import com.dp.trains.model.entities.FinancialDataEntity;
import com.googlecode.jmapper.annotations.JMap;
import com.poiji.annotation.ExcelCell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialDataDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineNumber", classes = FinancialDataEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "lineType", classes = FinancialDataEntity.class)
    private String lineType;

    @ExcelCell(2)
    @JMap(value = "lineLength", classes = FinancialDataEntity.class)
    private Integer lineLength;

    @ExcelCell(3)
    @JMap(value = "isElectrified", classes = FinancialDataEntity.class)
    private String isElectrified;

    @ExcelCell(4)
    @JMap(value = "accountGroup1", classes = FinancialDataEntity.class)
    private Integer accountGroup1;

    @ExcelCell(5)
    @JMap(value = "accountGroup21", classes = FinancialDataEntity.class)
    private Integer accountGroup21;

    @ExcelCell(6)
    @JMap(value = "accountGroup40", classes = FinancialDataEntity.class)
    private Integer accountGroup40;

    @ExcelCell(7)
    @JMap(value = "accountGroup42", classes = FinancialDataEntity.class)
    private Integer accountGroup42;

    @ExcelCell(8)
    @JMap(value = "accountGroup43", classes = FinancialDataEntity.class)
    private Integer accountGroup43;

    @ExcelCell(9)
    @JMap(value = "accountGroup44", classes = FinancialDataEntity.class)
    private Integer accountGroup44;

    @ExcelCell(10)
    @JMap(value = "accountGroup45", classes = FinancialDataEntity.class)
    private Integer accountGroup45;

    @ExcelCell(11)
    @JMap(value = "accountGroup46", classes = FinancialDataEntity.class)
    private Integer accountGroup46;

    @ExcelCell(12)
    @JMap(value = "accountGroup47", classes = FinancialDataEntity.class)
    private Integer accountGroup47;

    @ExcelCell(13)
    @JMap(value = "accountGroup48", classes = FinancialDataEntity.class)
    private Integer accountGroup48;

    @ExcelCell(14)
    @JMap(value = "accountGroup49", classes = FinancialDataEntity.class)
    private Integer accountGroup49;
}
