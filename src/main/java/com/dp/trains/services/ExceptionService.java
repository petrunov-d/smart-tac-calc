package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.model.dto.ExceptionDto;
import com.dp.trains.model.entities.ExceptionEntity;
import com.dp.trains.repository.ExceptionRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExceptionService {

    private final ExceptionRepository exceptionRepository;

    @Qualifier("exceptionMapper")
    private final DefaultDtoEntityMapperService<ExceptionDto, ExceptionEntity> exceptionMapper;

    @YearAgnostic
    @Transactional(noRollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public ExceptionEntity saveException(ExceptionDto exceptionDto) {

        ExceptionEntity exceptionEntity = exceptionMapper.mapEntity(exceptionDto);
        exceptionEntity.setId(UUID.randomUUID());

        log.info(this.getClass().getSimpleName() + " save: " + exceptionEntity.toString());

        return exceptionRepository.saveAndFlush(exceptionEntity);
    }
}