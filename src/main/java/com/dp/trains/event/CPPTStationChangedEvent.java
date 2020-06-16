package com.dp.trains.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.IntegralDataTypeHolder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CPPTStationChangedEvent {

    private String selectedStation;
    private Boolean isKeyStation;
    private Integer lineNumber;
}
