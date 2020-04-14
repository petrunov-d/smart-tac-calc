package com.dp.trains.ui.components.common;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.GRID_FILTER_ROW_PLACEHOLDER;

@Slf4j
public class FilteringTextField extends TextField {

    public FilteringTextField() {

        super();

        this.setValueChangeMode(ValueChangeMode.EAGER);
        this.setSizeFull();
        this.setPlaceholder(getTranslation(GRID_FILTER_ROW_PLACEHOLDER));
    }
}