package com.dp.trains.model.entities.listeners;

import com.dp.trains.model.entities.RailStationEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
public class RailStationEntityListener {

    @PreUpdate
    @PrePersist
    private void setIsElectrified(Object object) {

        if (object instanceof RailStationEntity) {

            //       RailStationEntity railStationEntity = (RailStationEntity) object;

//            if (railStationEntity.getIsKeyStation() == null) {
//
//                railStationEntity.setIsKeyStation(false);
//            }
        }
    }
}
