package com.dp.trains.model.dto;

import com.dp.trains.model.entities.StrategicCoefficientEntity;
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
public class StrategicCoefficientDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "code", classes = StrategicCoefficientEntity.class)
    private Integer code;

    @ExcelCell(1)
    @JMap(value = "name", classes = StrategicCoefficientEntity.class)
    private String name;

    @ExcelCell(2)
    @JMap(value = "coefficient", classes = StrategicCoefficientEntity.class)
    private Double coefficient;
}
