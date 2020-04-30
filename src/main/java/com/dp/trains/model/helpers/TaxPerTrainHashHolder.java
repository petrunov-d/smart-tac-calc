package com.dp.trains.model.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaxPerTrainHashHolder {

    private int index;
    private Long hash;
}
