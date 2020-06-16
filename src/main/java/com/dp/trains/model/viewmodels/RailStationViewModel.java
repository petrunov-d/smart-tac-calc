package com.dp.trains.model.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RailStationViewModel {

    private String railStation;
    private Integer lineNumber;
    private Boolean isKeyStation;
    private Boolean keyStationIsFirst;
}
