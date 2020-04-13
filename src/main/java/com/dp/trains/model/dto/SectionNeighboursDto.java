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

    private Integer lineNumber;
    private String typeOfLine;
    private RailStationEntity source;
    private RailStationEntity destination;
    private RailStationEntity nonKeyStation;
    private Boolean isElectrified;
    private Double unitPrice;
    private Double kilometersBetweenStations;
    private Boolean isKeyStation;
    private int rowIndex;

    public String getDisplayName() {

        return isKeyStation ? destination.getStation() : nonKeyStation.getStation();
    }
}