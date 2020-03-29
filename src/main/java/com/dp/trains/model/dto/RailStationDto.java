package com.dp.trains.model.dto;

import com.dp.trains.model.entities.RailStationEntity;
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
public class RailStationDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineNumber", classes = RailStationEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "station", classes = RailStationEntity.class)
    private String station;

    @ExcelCell(2)
    @JMap(value = "type", classes = RailStationEntity.class)
    private String type;

    @ExcelCell(3)
    @JMap(value = "isKeyStation", classes = RailStationEntity.class)
    private Boolean isKeyStation;

    @ExcelCell(4)
    @JMap(value = "country", classes = RailStationEntity.class)
    private String country;
}
