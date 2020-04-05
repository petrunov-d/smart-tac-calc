package com.dp.trains.model.entities.listeners;

import com.dp.trains.model.entities.SectionEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
public class SectionEntityListener {

    @PreUpdate
    @PrePersist
    private void setIsElectrified(Object object) {

        if (object instanceof SectionEntity) {

            SectionEntity sectionEntity = (SectionEntity) object;

            if (sectionEntity.getIsElectrified() == null) {

                sectionEntity.setIsElectrified(false);
            }
        }
    }
}