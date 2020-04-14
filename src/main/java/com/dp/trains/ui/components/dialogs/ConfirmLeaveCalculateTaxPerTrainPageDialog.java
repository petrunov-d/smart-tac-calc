package com.dp.trains.ui.components.dialogs;

import com.dp.trains.event.CPPTResetPageEvent;
import com.dp.trains.utils.EventBusHolder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ConfirmLeaveCalculateTaxPerTrainPageDialog extends SmartTACCalcDialogBase {

    public ConfirmLeaveCalculateTaxPerTrainPageDialog(BeforeLeaveEvent beforeLeaveEvent) {

        super();

        VerticalLayout verticalLayout = new VerticalLayout();

        H3 h3Heading = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CONFIRM_LEAVE_MESSAGE));

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(h3Heading);

        Button okButton = new Button(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK),
                VaadinIcon.CHECK_CIRCLE_O.create());

        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        okButton.addClickListener(event -> {

            EventBusHolder.getEventBus().post(new CPPTResetPageEvent());
            beforeLeaveEvent.getContinueNavigationAction().proceed();
            this.close();
        });

        cancel.addClickListener(event -> this.close());

        verticalLayout.add(h3Heading);
        verticalLayout.add(new HorizontalLayout(okButton, cancel));
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }
}