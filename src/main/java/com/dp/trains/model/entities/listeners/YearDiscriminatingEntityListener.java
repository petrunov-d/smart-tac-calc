package com.dp.trains.model.entities.listeners;

import com.dp.trains.model.entities.YearDiscriminatingEntity;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
public class YearDiscriminatingEntityListener {

    @PreUpdate
    @PreRemove
    @PrePersist
    private void setYear(Object object) {

        if (object instanceof YearDiscriminatingEntity) {

            YearDiscriminatingEntity yearDiscriminatingEntity = (YearDiscriminatingEntity) object;

            if (yearDiscriminatingEntity.getShouldUpdateYear()) {
                log.info("Modifying the following entity:" + object.toString());

                ((YearDiscriminatingEntity) object).setYear(SelectedYearPerUserHolder.getForCurrentlyLoggedInUser());

            } else {

                log.info("Shouldn't update year for entitiy:" + object.toString());
            }
        }
    }
}