package com.dp.trains.model.dto;

import com.dp.trains.model.entities.UnitPriceEntity;
import com.googlecode.jmapper.annotations.JMap;
import com.poiji.annotation.ExcelCell;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"unitPrice"})
public class UnitPriceDto implements ExcelImportDto {

    @ExcelCell(0)
    @JMap(value = "code", classes = UnitPriceEntity.class)
    private Integer code;

    @ExcelCell(1)
    @JMap(value = "name", classes = UnitPriceEntity.class)
    private String name;

    @ExcelCell(2)
    @JMap(value = "measure", classes = UnitPriceEntity.class)
    private String measure;

    @ExcelCell(3)
    @JMap(value = "unitPrice", classes = UnitPriceEntity.class)
    private Double unitPrice;
}
