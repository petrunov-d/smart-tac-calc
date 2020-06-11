package com.dp.trains.ui.components.dialogs;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static com.dp.trains.utils.LocaleKeys.COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK;
import static com.dp.trains.utils.LocaleKeys.SHARED_BUTTON_TEXT_CANCEL;

public class ConfirmDialog extends SmartTACCalcDialogBase {

    private final Button cancelButton;
    private final Button okButton;

    private ConfirmDialog(String title, ComponentEventListener<ClickEvent<Button>> okButtonListener) {

        super();

        H3 heading = new H3(title);

        okButton = new Button(getTranslation(COPY_DATA_FROM_PREVIOUS_YEAR_DIALOG_BUTTON_OK),
                VaadinIcon.CHECK_CIRCLE_O.create());

        this.cancelButton = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        okButton.addClickListener(okButtonListener);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(heading);
        verticalLayout.add(new HorizontalLayout(okButton, this.cancelButton));
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }

    private void addCancelButtonListener(ComponentEventListener<ClickEvent<Button>> cancelButtonListener) {

        this.cancelButton.addClickListener(cancelButtonListener);
    }

    private void addOkButtonListener(ComponentEventListener<ClickEvent<Button>> cancelButtonListener) {

        this.okButton.addClickListener(cancelButtonListener);
    }

    public static class Builder {

        private String title;
        private ComponentEventListener<ClickEvent<Button>> okButtonListener;
        private ComponentEventListener<ClickEvent<Button>> cancelButtonListener;

        public Builder() {

        }

        public Builder withTitle(String title) {

            this.title = title;

            return this;
        }

        public Builder withOkButtonListener(ComponentEventListener<ClickEvent<Button>> okButtonListener) {

            this.okButtonListener = okButtonListener;

            return this;
        }

        public Builder withCancelButtonListener(ComponentEventListener<ClickEvent<Button>> cancelButtonListener) {

            this.cancelButtonListener = cancelButtonListener;

            return this;
        }

        public ConfirmDialog build() {

            ConfirmDialog confirmDialog = new ConfirmDialog(this.title, this.okButtonListener);

            if (this.cancelButtonListener != null) {

                confirmDialog.addCancelButtonListener(this.cancelButtonListener);
            }

            confirmDialog.addOkButtonListener((ComponentEventListener<ClickEvent<Button>>) event -> confirmDialog.close());
            confirmDialog.addCancelButtonListener((ComponentEventListener<ClickEvent<Button>>) event -> confirmDialog.close());

            return confirmDialog;
        }
    }
}