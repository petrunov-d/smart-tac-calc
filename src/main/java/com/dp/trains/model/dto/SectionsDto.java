package com.dp.trains.model.dto;

import com.dp.trains.model.entities.SectionEntity;
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
public class SectionsDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineNumber", classes = SectionEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "lineType", classes = SectionEntity.class)
    private String lineType;

    @ExcelCell(2)
    @JMap(value = "firstKeyPoint", classes = SectionEntity.class)
    private String firstKeyPoint;

    @ExcelCell(3)
    @JMap(value = "lastKeyPoint", classes = SectionEntity.class)
    private String lastKeyPoint;

    @ExcelCell(4)
    @JMap(value = "kilometersBetweenStations", classes = SectionEntity.class)
    private Double kilometersBetweenStations;

    @ExcelCell(5)
    @JMap(value = "isElectrified", classes = SectionEntity.class)
    private Boolean isElectrified;

    @ExcelCell(6)
    @JMap(value = "unitPrice", classes = SectionEntity.class)
    private Double unitPrice;
}
