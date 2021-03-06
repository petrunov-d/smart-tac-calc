package com.dp.trains.ui.components.csp;

import com.dp.trains.model.dto.UnitPriceDto;
import com.dp.trains.services.UnitPriceService;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.utils.SelectedYearPerUserHolder;
import com.google.common.base.Joiner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Map;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class CalculateSinglePriceByAverageContainer extends VerticalLayout {

    public CalculateSinglePriceByAverageContainer(UnitPriceService unitPriceService) {

        super();

        Integer currentYear = SelectedYearPerUserHolder.getForUser(SecurityContextHolder.getContext()
                .getAuthentication().getName());

        H3 h3 = new H3(String.format("%s %d %s", getTranslation(CALCULATE_SINGLE_PRICE_DESCRIPTION_ONE), currentYear,
                getTranslation(CALCULATE_SINGLE_PRICE_DESCRIPTION_TWO)));

        H3 backtrackTitle = new H3(getTranslation(CALCULATE_SINGLE_PRICE_INPUT_AMOUNT_OF_YEARS_TO_GO_BACK));
        IntegerField backtrackYearsField = new IntegerField(getTranslation(CALCULATE_SINGLE_PRICE_INPUT_YEARS_LABEL));
        backtrackYearsField.setValue(1);
        backtrackYearsField.setHasControls(true);
        backtrackYearsField.setMin(1);
        backtrackYearsField.setMax(20);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(backtrackTitle, backtrackYearsField);

        Button calculate = new Button(getTranslation(CALCULATE_SINGLE_PRICE_INPUT_YEARS_BUTTON_CALCULATE),
                VaadinIcon.CALC_BOOK.create());
        calculate.addClickListener(e -> {

            Integer yearsToGoBack = backtrackYearsField.getValue();

            Map<Integer, Boolean> validationResult =
                    unitPriceService.validateUnitPriceDataIntegrityForYears(yearsToGoBack, currentYear);

            if (validationResult.entrySet().size() > 0) {

                Dialog dialog = new BasicInfoDialog(String.format("%s %s %s",
                        getTranslation(CALCULATE_SINGLE_PRICE_ERROR_NO_DATA_FOR_PROVIDED_YEARS),
                        Joiner.on(", ").join(validationResult.keySet()),
                        getTranslation(CALCULATE_SINGLE_PRICE_ERROR_NO_DATA_FOR_PROVIDED_YEARS_HINT)));

                dialog.open();

            } else {

                Collection<UnitPriceDto> unitPriceDtoList = unitPriceService
                        .calculateSinglePriceForYears(yearsToGoBack, currentYear);
                unitPriceService.add(unitPriceDtoList);

                Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_SINGLE_PRICE_CALCULATION_SUCCESS));
                dialog.open();
            }
        });

        this.add(h3);
        this.add(horizontalLayout);
        this.add(calculate);
        this.setSizeFull();

        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }
}