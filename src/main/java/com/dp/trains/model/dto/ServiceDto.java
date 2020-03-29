package com.dp.trains.model.dto;

import com.dp.trains.model.entities.ServiceEntity;
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
public class ServiceDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "lineNumber", classes = ServiceEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "metric", classes = ServiceEntity.class)
    private String metric;

    @ExcelCell(2)
    @JMap(value = "unitPrice", classes = ServiceEntity.class)
    private Double unitPrice;

    @ExcelCell(3)
    @JMap(value = "name", classes = ServiceEntity.class)
    private String name;

    @ExcelCell(4)
    @JMap(value = "type", classes = ServiceEntity.class)
    private String type;
}
