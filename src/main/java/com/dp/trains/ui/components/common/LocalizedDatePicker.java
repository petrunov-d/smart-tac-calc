package com.dp.trains.ui.components.common;

import com.vaadin.flow.component.datepicker.DatePicker;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class LocalizedDatePicker extends DatePicker {

    public LocalizedDatePicker() {

        super();

        this.setI18n(new DatePickerI18n()
                .setWeek(getTranslation(DATE_PICKER_WEEK))
                .setCalendar(getTranslation(DATE_PICKER_CALENDAR))
                .setClear(getTranslation(DATE_PICKER_CLEAR))
                .setToday(getTranslation(DATE_PICKER_TODAY))
                .setCancel(getTranslation(DATE_PICKER_CANCEL))
                .setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList(
                        getTranslation(DATE_PICKER_JANUARY),
                        getTranslation(DATE_PICKER_FEBRUARY),
                        getTranslation(DATE_PICKER_MARCH),
                        getTranslation(DATE_PICKER_APRIL),
                        getTranslation(DATE_PICKER_MAY),
                        getTranslation(DATE_PICKER_JUNE),
                        getTranslation(DATE_PICKER_JULY),
                        getTranslation(DATE_PICKER_AUGUST),
                        getTranslation(DATE_PICKER_SEPTEMBER),
                        getTranslation(DATE_PICKER_OCTOBER),
                        getTranslation(DATE_PICKER_NOVEMBER),
                        getTranslation(DATE_PICKER_DECEMBER)))
                .setWeekdays(Arrays.asList(
                        getTranslation(DATE_PICKER_MONDAY),
                        getTranslation(DATE_PICKER_TUESDAY),
                        getTranslation(DATE_PICKER_WEDNESDAY),
                        getTranslation(DATE_PICKER_THURSDAY),
                        getTranslation(DATE_PICKER_FRIDAY),
                        getTranslation(DATE_PICKER_SATURDAY),
                        getTranslation(DATE_PICKER_SUNDAY)))
                .setWeekdaysShort(Arrays.asList(
                        getTranslation(DATE_PICKER_SHORT_MONDAY),
                        getTranslation(DATE_PICKER_SHORT_TUESDAY),
                        getTranslation(DATE_PICKER_SHORT_WEDNESDAY),
                        getTranslation(DATE_PICKER_SHORT_THURSDAY),
                        getTranslation(DATE_PICKER_SHORT_FRIDAY),
                        getTranslation(DATE_PICKER_SHORT_SATURDAY),
                        getTranslation(DATE_PICKER_SHORT_SUNDAY))));
    }
}