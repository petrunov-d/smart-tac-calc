package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.RailStationDto;
import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.RailStationService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
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
public class AddRailStationDialog extends AddDialogBase {

    public AddRailStationDialog(Grid currentlyActiveGrid, RailStationService railStationService,
                                LineNumberService lineNumberService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<RailStationDto> binder = new Binder<>();

        RailStationDto railStationDto = new RailStationDto();

        ComboBox<Integer> lineNumber = new ComboBox<>();

        lineNumber.setItems(lineNumberService.getLineNumbersAsInts());
        lineNumber.addValueChangeListener(event -> binder.validate());
        lineNumber.setRequiredIndicatorVisible(true);

        TextArea station = new TextArea();

        station.setValueChangeMode(ValueChangeMode.EAGER);
        station.addValueChangeListener(event -> binder.validate());
        station.setRequiredIndicatorVisible(true);

        TextArea type = new TextArea();

        type.setValueChangeMode(ValueChangeMode.EAGER);
        type.addValueChangeListener(event -> binder.validate());
        type.setRequiredIndicatorVisible(true);

        Checkbox isKeyStation = new Checkbox();

        isKeyStation.addValueChangeListener(event -> binder.validate());
        isKeyStation.setRequiredIndicatorVisible(true);

        TextArea country = new TextArea();
        country.setValueChangeMode(ValueChangeMode.EAGER);
        country.addValueChangeListener(event -> binder.validate());
        country.setRequiredIndicatorVisible(false);

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(RailStationDto::getLineNumber, RailStationDto::setLineNumber);

        binder.forField(station)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_RAIL_STATION_COLUMN_VALIDATION_STATION)))
                .bind(RailStationDto::getStation, RailStationDto::setStation);

        binder.forField(type)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_TYPE)))
                .bind(RailStationDto::getStation, RailStationDto::setStation);

        binder.forField(isKeyStation)
                .asRequired()
                .bind(RailStationDto::getIsKeyStation, RailStationDto::setIsKeyStation);

        binder.forField(country)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation("Country is required.")))
                .bind(RailStationDto::getCountry, RailStationDto::setCountry);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(lineNumber, getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        layoutWithBinder.addFormItem(station, getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION));
        layoutWithBinder.addFormItem(type, getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE));
        layoutWithBinder.addFormItem(isKeyStation, getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_IS_KEY_STATION));
        layoutWithBinder.addFormItem(country, getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_COUNTRY));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(railStationDto)) {

                ListDataProvider<RailStationEntity> dataProvider =
                        (ListDataProvider<RailStationEntity>) currentlyActiveGrid.getDataProvider();

                RailStationEntity railStationEntity = railStationService.add(railStationDto);
                dataProvider.getItems().add(railStationEntity);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<RailStationDto> validate = binder.validate();

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
            station.setValue("");
            type.setValue("");
            isKeyStation.setValue(false);
            country.setValue("");
            this.close();
        });

        reset.addClickListener(event -> {

            binder.readBean(null);
            lineNumber.setValue(null);
            station.setValue("");
            type.setValue("");
            country.setValue("");
            isKeyStation.setValue(false);
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_RAIL_STATION_TITLE), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
