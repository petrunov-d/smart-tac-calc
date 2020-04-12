package com.dp.trains.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exception_log")
@ToString(exclude = {"stackTrace"})
public class ExceptionEntity {

    @Id
    private UUID id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "exception_name")
    private String exceptionName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "stacktrace")
    private String stackTrace;
}
