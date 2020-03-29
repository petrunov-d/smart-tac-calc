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
    @JMap(value = "lineNumber", classes = TrafficDataEntity.class)
    private Integer lineNumber;

    @ExcelCell(1)
    @JMap(value = "rank", classes = TrafficDataEntity.class)
    private String rank;

    @ExcelCell(2)
    @JMap(value = "lineLength", classes = TrafficDataEntity.class)
    private Integer lineLength;

    @ExcelCell(3)
    @JMap(value = "isElectrified", classes = TrafficDataEntity.class)
    private String isElectrified;

    @ExcelCell(4)
    @JMap(value = "trainType", classes = TrafficDataEntity.class)
    private Integer trainType;

    @ExcelCell(5)
    @JMap(value = "freightTrafficTrainKilometers", classes = TrafficDataEntity.class)
    private Integer freightTrafficTrainKilometers;

    @ExcelCell(6)
    @JMap(value = "freightTrafficTrainBrutoTonKilometers", classes = TrafficDataEntity.class)
    private Integer freightTrafficTrainBrutoTonKilometers;

    @ExcelCell(7)
    @JMap(value = "passengerTrafficTrainKilometers", classes = TrafficDataEntity.class)
    private Integer passengerTrafficTrainKilometers;

    @ExcelCell(8)
    @JMap(value = "passengerTrafficTrainBrutoTonKilometers", classes = TrafficDataEntity.class)
    private Integer passengerTrafficTrainBrutoTonKilometers;
}
