package com.dp.trains.model.dto;

import com.dp.trains.model.entities.RailStationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SectionNeighboursDto {

    private Integer rowIndex;
    private Integer lineNumber;
    private String typeOfLine;
    private RailStationEntity originalSource;
    private RailStationEntity originalDestination;
    private RailStationEntity nonKeyStation;
    private Boolean isElectrified;
    private Double kilometersBetweenStations;
    private Boolean isKeyStation;

    private RailStationEntity currentSource;
    private RailStationEntity currentDestination;
}