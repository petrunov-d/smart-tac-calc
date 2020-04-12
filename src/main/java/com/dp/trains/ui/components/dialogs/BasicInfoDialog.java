package com.dp.trains.ui.components.dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK;

@Slf4j
public class BasicInfoDialog extends Dialog {

    public BasicInfoDialog(String message) {

        super();

        VerticalLayout verticalLayout = new VerticalLayout();

        H3 errorText = new H3(message);

        Button ok = new Button(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK), new Icon(VaadinIcon.CLOSE_SMALL));
        ok.addClickListener(e -> this.close());

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        verticalLayout.add(errorText);
        verticalLayout.add(ok);

        this.add(verticalLayout);
    }
}
