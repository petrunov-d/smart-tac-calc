package com.dp.trains.ui.components.dialogs.add;

import com.dp.trains.model.dto.StrategicCoefficientDto;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.services.StrategicCoefficientService;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
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
public class AddStrategicCoefficientDialog extends SmartTACCalcDialogBase {

    public AddStrategicCoefficientDialog(Grid currentlyActiveGrid, StrategicCoefficientService strategicCoefficientService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<StrategicCoefficientDto> binder = new Binder<>();

        StrategicCoefficientDto strategicCoefficientDto = new StrategicCoefficientDto();

        IntegerField code = new IntegerField();

        code.setValueChangeMode(ValueChangeMode.EAGER);
        code.addValueChangeListener(event -> binder.validate());
        code.setRequiredIndicatorVisible(true);

        TextArea name = new TextArea();
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);

        NumberField coefficient = new NumberField();
        coefficient.setValueChangeMode(ValueChangeMode.EAGER);
        coefficient.addValueChangeListener(event -> binder.validate());

        binder.forField(code)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(
                        getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_CODE_MESSAGE)))
                .bind(StrategicCoefficientDto::getCode, StrategicCoefficientDto::setCode);

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredVarcharStringValidator(
                        getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .bind(StrategicCoefficientDto::getName, StrategicCoefficientDto::setName);

        binder.forField(coefficient)
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(
                        getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_VALIDATION_STRATEGIC_COEFFICIENT)))
                .bind(StrategicCoefficientDto::getCoefficient, StrategicCoefficientDto::setCoefficient);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(code, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE));
        layoutWithBinder.addFormItem(name, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(coefficient, getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_HEADER_COEFFICIENT));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(strategicCoefficientDto)) {

                ListDataProvider<StrategicCoefficientEntity> dataProvider =
                        (ListDataProvider<StrategicCoefficientEntity>) currentlyActiveGrid.getDataProvider();

                StrategicCoefficientEntity strategicCoefficientEntity = strategicCoefficientService
                        .add(strategicCoefficientDto);
                dataProvider.getItems().add(strategicCoefficientEntity);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<StrategicCoefficientDto> validate = binder.validate();

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
            code.setValue(null);
            name.setValue("");
            coefficient.setValue(null);
            this.close();
        });

        reset.addClickListener(event -> {

            binder.readBean(null);
            code.setValue(null);
            name.setValue("");
            coefficient.setValue(null);
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_STRATEGIC_COEFFICIENT_TITLE), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}