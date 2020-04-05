package com.dp.trains.model.dto;

import com.dp.trains.model.entities.LineTypeEntity;
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
public class LineTypeDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineType", classes = LineTypeEntity.class)
    private String lineType;

    @ExcelCell(1)
    @JMap(value = "name", classes = LineTypeEntity.class)
    private String name;

    @ExcelCell(2)
    @JMap(value = "code", classes = LineTypeEntity.class)
    private String code;
}
