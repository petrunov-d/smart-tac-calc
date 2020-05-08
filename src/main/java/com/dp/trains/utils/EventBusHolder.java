package com.dp.trains.utils;

import com.google.common.eventbus.EventBus;

public class EventBusHolder {

    private static EventBus instance;

    private EventBusHolder() {
    }

    public static EventBus getEventBus() {

        if (instance == null) {

            synchronized (EventBusHolder.class) {

                if (instance == null) {

                    instance = new EventBus();
                }
            }
        }

        return instance;
    }
}