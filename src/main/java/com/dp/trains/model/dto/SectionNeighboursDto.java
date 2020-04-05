package com.dp.trains.model.dto;

import com.dp.trains.model.entities.RailStationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SectionNeighboursDto {

    private Integer lineNumber;
    private String typeOfLine;
    private RailStationEntity destination;
    private Boolean isElectrified;
    private Double unitPrice;
    private Double kilometersBetweenStations;
    private Set<SubSectionDto> subSectionDtoList;
    private Set<DisplayableStationDto> displayableStationNames;
    private int rowCount;
}