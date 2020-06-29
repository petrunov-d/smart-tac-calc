package com.dp.trains.services;

import com.dp.trains.annotation.YearAgnostic;
import com.dp.trains.repository.DBScrubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DBScrubService {

    private final DBScrubRepository dbScrubRepository;

    @YearAgnostic
    @Transactional
    public void scrubDb() {

        log.info("Scrubbing DB...");

        this.dbScrubRepository.runNativeScrub();

        log.info("DB Scrubbed succesfully.");
    }
}