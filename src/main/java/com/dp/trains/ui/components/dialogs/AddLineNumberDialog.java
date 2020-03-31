package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.LineNumberDto;
import com.dp.trains.model.entities.LineNumberEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class AddLineNumberDialog extends AddDialogBase {

    public AddLineNumberDialog(Grid currentlyActiveGrid, LineNumberService lineNumberService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<LineNumberDto> binder = new Binder<>();

        LineNumberDto lineNumberDto = new LineNumberDto();

        IntegerField lineNumber = new IntegerField();

        lineNumber.setValueChangeMode(ValueChangeMode.EAGER);
        lineNumber.addValueChangeListener(event -> binder.validate());
        lineNumber.setRequiredIndicatorVisible(true);

        TextArea description = new TextArea();

        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.addValueChangeListener(event -> binder.validate());
        description.setRequiredIndicatorVisible(true);

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(LineNumberDto::getLineNumber, LineNumberDto::setLineNumber);

        binder.forField(description)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_NUMBER_FORM_ITEM_DESCRIPTION_VALIDATION)))
                .bind(LineNumberDto::getDescription, LineNumberDto::setDescription);

        layoutWithBinder.addFormItem(lineNumber, getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        layoutWithBinder.addFormItem(description, getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(lineNumberDto)) {

                ListDataProvider<LineNumberEntity> dataProvider =
                        (ListDataProvider<LineNumberEntity>) currentlyActiveGrid.getDataProvider();

                LineNumberEntity railStationEntity = lineNumberService.add(lineNumberDto);
                dataProvider.getItems().add(railStationEntity);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<LineNumberDto> validate = binder.validate();

                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));

                log.warn(errorText);
            }
        });

        cancel.addClickListener(event -> {

            binder.readBean(null);
            lineNumber.setValue(null);
            description.setValue("");
            this.close();
        });

        reset.addClickListener(event -> {

            binder.readBean(null);
            lineNumber.setValue(null);
            description.setValue("");
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_LINE_NUMBER_LABEL),
                layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}