package com.dp.trains.ui.components.dialogs;

import com.dp.trains.services.UnitPriceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ConfirmClearUnitPriceDialog extends SmartTACCalcDialogBase {

    public ConfirmClearUnitPriceDialog(UnitPriceService unitPriceService) {

        super();

        VerticalLayout verticalLayout = new VerticalLayout();

        H3 h3Heading = new H3(getTranslation(CONFIRM_DELETE_UNIT_PRICE));

        Button okButton = new Button(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK),
                VaadinIcon.CHECK_CIRCLE_O.create());

        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        okButton.addClickListener(event -> {

            unitPriceService.deleteAll();
            this.close();
        });

        cancel.addClickListener(event -> this.close());

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(h3Heading);
        verticalLayout.add(new HorizontalLayout(okButton, cancel));
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }
}
