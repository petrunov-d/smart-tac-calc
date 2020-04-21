package com.dp.trains.model.dto;

import com.dp.trains.model.entities.MarkupCoefficientEntity;
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
public class MarkupCoefficientDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "code", classes = MarkupCoefficientEntity.class)
    private String code;

    @ExcelCell(1)
    @JMap(value = "name", classes = MarkupCoefficientEntity.class)
    private String name;

    @ExcelCell(2)
    @JMap(value = "coefficient", classes = MarkupCoefficientEntity.class)
    private Double coefficient;
}
