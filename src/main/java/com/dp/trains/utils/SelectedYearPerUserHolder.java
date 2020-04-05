package com.dp.trains.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

@Slf4j
public class SelectedYearPerUserHolder {

    private static Map<String, Integer> backingMap = Maps.newConcurrentMap();

    public static void addOrUpdate(String username, Integer year) {

        log.info(String.format("Setting year to: %d for user: %s", year, username));

        backingMap.put(username.toLowerCase(), year);
    }

    public static Integer getForUser(String user) {

        Integer year = backingMap.get(user.toLowerCase());

        log.debug(String.format("Got year %d for user: %s", year, user));

        return year;
    }

    public static void remove(String user) {

        backingMap.remove(user);
    }

    public static Integer getForCurrentlyLoggedInUser() {

        log.debug("Current Map state: " + Joiner.on(",").withKeyValueSeparator(":").join(backingMap));

        return backingMap.get(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase());
    }
}
