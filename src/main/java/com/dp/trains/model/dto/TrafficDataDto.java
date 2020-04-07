package com.dp.trains.model.dto;

import com.dp.trains.model.entities.TrafficDataEntity;
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
public class TrafficDataDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "directCostDescrption", classes = TrafficDataEntity.class)
    private String directCostDescrption;

    @ExcelCell(1)
    @JMap(value = "directCostValue", classes = TrafficDataEntity.class)
    private Double directCostValue;

    @ExcelCell(2)
    @JMap(value = "code", classes = TrafficDataEntity.class)
    private String code;

}