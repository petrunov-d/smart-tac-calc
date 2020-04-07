package com.dp.trains.utils;


public class SmartTacCalcContext {

    private static Boolean shouldRefreshInitially = true;

    public static Boolean getShouldRefreshInitially() {
        return shouldRefreshInitially;
    }

    public static void setShouldRefreshInitially(Boolean val) {

        shouldRefreshInitially = val;
    }
}
