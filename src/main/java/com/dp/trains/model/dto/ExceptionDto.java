package com.dp.trains.model.dto;

import com.dp.trains.model.entities.ExceptionEntity;
import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    @JMap(value = "timestamp", classes = ExceptionEntity.class)
    private LocalDateTime timestamp;

    @JMap(value = "exceptionName", classes = ExceptionEntity.class)
    private String exceptionName;

    @JMap(value = "methodName", classes = ExceptionEntity.class)
    private String methodName;

    @JMap(value = "stackTrace", classes = ExceptionEntity.class)
    private String stackTrace;
}
