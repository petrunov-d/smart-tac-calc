package com.dp.trains.aspect;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.entities.YearDiscriminatingEntity;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SessionFilterDecoratingAspect {

    private final EntityManager entityManager;

    @Pointcut("within(com.dp.trains.services.*)")
    public void allServiceLayerMethods() {
    }

    @Order(1)
    @Before("allServiceLayerMethods()")
    public void decorateJoinPoint(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        boolean isYearAgnosticMethod = Arrays.stream((methodSignature)
                .getMethod().getAnnotations()).anyMatch(x -> x instanceof YearAgnostic);

        if (!isYearAgnosticMethod && methodSignature.getMethod().isAnnotationPresent(Transactional.class)) {

            if (entityManager.unwrap(Session.class) == null) {

                throw new NullPointerException("Session Factory is not a Hibernate Factory");
            }

            Session session = entityManager.unwrap(Session.class);

            session.enableFilter(YearDiscriminatingEntity.YEAR_FILTER).setParameter(YearDiscriminatingEntity.YEAR,
                    SelectedYearPerUserHolder.getForCurrentlyLoggedInUser());
        }
    }
}