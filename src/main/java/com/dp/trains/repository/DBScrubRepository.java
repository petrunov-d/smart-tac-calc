package com.dp.trains.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBScrubRepository {

    @Autowired
    private EntityManager entityManager;

    @Modifying
    public void runNativeScrub() {

        Object result = entityManager.createNativeQuery("TRUNCATE TABLE public.carrier_company CASCADE;" +
                " TRUNCATE TABLE public.exception_log CASCADE;" +
                " TRUNCATE TABLE public.financial_data CASCADE;" +
                " TRUNCATE TABLE public.line_number CASCADE;" +
                " TRUNCATE TABLE public.line_type CASCADE;" +
                " TRUNCATE TABLE public.markup_coefficient CASCADE;" +
                " TRUNCATE TABLE public.rail_station CASCADE;" +
                " TRUNCATE TABLE public.section CASCADE;" +
                " TRUNCATE TABLE public.service CASCADE;" +
                " TRUNCATE TABLE public.service_charges_per_train CASCADE;" +
                " TRUNCATE TABLE public.strategic_coefficient CASCADE;" +
                " TRUNCATE TABLE public.sub_section CASCADE;" +
                " TRUNCATE TABLE public.tax_for_services_per_train CASCADE;" +
                " TRUNCATE TABLE public.tax_per_train CASCADE;" +
                " TRUNCATE TABLE public.traffic_data CASCADE;" +
                " TRUNCATE TABLE public.train_types CASCADE;" +
                " TRUNCATE TABLE public.unit_price CASCADE;" +
                " TRUNCATE TABLE public.unit_price CASCADE;")
                .getSingleResult();

        log.info(result.toString());
    }
}
