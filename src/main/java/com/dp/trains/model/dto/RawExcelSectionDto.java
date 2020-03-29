package com.dp.trains.model.dto;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelUnknownCells;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawExcelSectionDto implements ExcelImportDto {

    @ExcelCell(0)
    private Integer lineNumber;

    @ExcelCell(1)
    private String lineType;

    @ExcelCell(2)
    private String firstKeyPoint;

    @ExcelCell(3)
    private String lastKeyPoint;

    @ExcelCell(4)
    private Double kilometersBetweenStations;

    @ExcelCell(5)
    private String isElectrified;

    @ExcelCellName("Unit price")
    private Double unitPrice;

    @ExcelUnknownCells
    private Map<String, String> unknownCells;
}