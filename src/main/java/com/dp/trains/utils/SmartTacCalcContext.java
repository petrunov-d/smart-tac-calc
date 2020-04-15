package com.dp.trains.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartTacCalcContext {

    private static SmartTacCalcContext smartTacCalcContext;

    public static SmartTacCalcContext get() {

        if (smartTacCalcContext == null) {

            synchronized (SmartTacCalcContext.class) {

                if (smartTacCalcContext == null) {

                    smartTacCalcContext = new SmartTacCalcContext();
                }
            }
        }

        return smartTacCalcContext;
    }
}