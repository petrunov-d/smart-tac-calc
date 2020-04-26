package com.dp.trains.model.dto;

import com.dp.trains.model.entities.TrainTypeEntity;
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
public class TrainTypeDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "code", classes = TrainTypeEntity.class)
    private String code;

    @ExcelCell(1)
    @JMap(value = "name", classes = TrainTypeEntity.class)
    private String name;
}
