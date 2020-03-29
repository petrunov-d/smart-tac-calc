package com.dp.trains.model.dto;

import com.dp.trains.model.entities.LineNumberEntity;
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
public class LineNumberDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineNumber", classes = LineNumberEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "description", classes = LineNumberEntity.class)
    private String description;
}
