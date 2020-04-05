package com.dp.trains.model.dto;

import com.dp.trains.model.entities.SubSectionEntity;
import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubSectionDto {

    @JMap(value = "kilometers", classes = SubSectionEntity.class)
    private Double kilometers;

    @JMap(value = "nonKeyStation", classes = SubSectionEntity.class)
    private String nonKeyStation;

    private Boolean isSelected;
}
