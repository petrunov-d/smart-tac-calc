package com.dp.trains.aspect;

import com.dp.trains.event.ExceptionRaisedEvent;
import com.dp.trains.model.dto.ExceptionDto;
import com.dp.trains.services.ExceptionService;
import com.dp.trains.utils.EventBusHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AfterExceptionThrownAspect {

    private final ExceptionService exceptionService;

    @Pointcut("within(com.dp.trains.common.*)")
    public void commonPackage() {
    }

    @Pointcut("within(com.dp.trains.model.*)")
    public void modelPackage() {
    }

    @Pointcut("within(com.dp.trains.repository.*)")
    public void repositoryPackage() {
    }

    @Pointcut("within(com.dp.trains.services.*)")
    public void servicePackage() {
    }

    @Pointcut("within(com.dp.trains.ui.*)")
    public void uiPackage() {
    }

    @Pointcut("within(com.dp.trains.utils.*)")
    public void utilsPackage() {
    }

    @AfterThrowing(pointcut = "servicePackage()", throwing = "error")
    public void afterThrowingServiceAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    @AfterThrowing(pointcut = "uiPackage()", throwing = "error")
    public void afterThrowingUIAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    @AfterThrowing(pointcut = "utilsPackage()", throwing = "error")
    public void afterThrowingUtilsAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    @AfterThrowing(pointcut = "repositoryPackage()", throwing = "error")
    public void afterThrowingRepositoryAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    @AfterThrowing(pointcut = "modelPackage()", throwing = "error")
    public void afterThrowingModelAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    @AfterThrowing(pointcut = "commonPackage()", throwing = "error")
    public void afterThrowingCommonAdvice(JoinPoint jp, Throwable error) {

        logException(jp, error);
    }

    private void logException(JoinPoint jp, Throwable error) {

        log.info("Method Signature: " + jp.getSignature());
        log.info("Exception: " + error);

        if (error instanceof JpaSystemException) {

            log.error("Got year filter exception, skipping...");
        } else {

            ExceptionDto exceptionDto = ExceptionDto
                    .builder()
                    .timestamp(LocalDateTime.now())
                    .exceptionName(error.getClass().getSimpleName())
                    .methodName(jp.getSignature().getDeclaringType() + "." + jp.getSignature().getName())
                    .stackTrace(ExceptionUtils.getStackTrace(error))
                    .build();

            this.exceptionService.saveException(exceptionDto);

            EventBusHolder.getEventBus().post(ExceptionRaisedEvent.builder().throwable(error).build());
        }
    }
}