package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DBScrubService {

    private final CarrierCompanyService carrierCompanyService;

    @YearAgnostic
    @Transactional
    public void scrubDb() {

        carrierCompanyService.deleteAll();
    }
}