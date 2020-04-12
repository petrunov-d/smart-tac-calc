package com.dp.trains.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionRaisedEvent {

    private Throwable throwable;
}
