package com.dp.trains.ui.components.dialogs.edit;

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
public class EditStrategicCoefficientDialog extends SmartTACCalcDialogBase {

    public EditStrategicCoefficientDialog(Grid currentlyActiveGrid, StrategicCoefficientService strategicCoefficientService,
                                          StrategicCoefficientEntity strategicCoefficientEntity) {
        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<StrategicCoefficientDto> binder = new Binder<>();

        StrategicCoefficientDto strategicCoefficientDto = new StrategicCoefficientDto();

        IntegerField code = new IntegerField();

        code.setValueChangeMode(ValueChangeMode.EAGER);
        code.addValueChangeListener(event -> binder.validate());
        code.setRequiredIndicatorVisible(true);
        code.setValue(strategicCoefficientEntity.getCode());

        TextArea name = new TextArea();
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);
        name.setValue(strategicCoefficientEntity.getName() == null ? "" : strategicCoefficientEntity.getName());

        NumberField coefficient = new NumberField();
        coefficient.setValueChangeMode(ValueChangeMode.EAGER);
        coefficient.addValueChangeListener(event -> binder.validate());
        coefficient.setValue(strategicCoefficientEntity.getCoefficient());

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
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(code, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE));
        layoutWithBinder.addFormItem(name, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(coefficient, getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_HEADER_COEFFICIENT));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(strategicCoefficientDto)) {

                ListDataProvider<StrategicCoefficientEntity> dataProvider =
                        (ListDataProvider<StrategicCoefficientEntity>) currentlyActiveGrid.getDataProvider();

                StrategicCoefficientEntity strategicCoefficientEntityUpdated = strategicCoefficientService
                        .update(strategicCoefficientDto, strategicCoefficientEntity.getId());

                dataProvider.getItems().remove(strategicCoefficientEntity);
                dataProvider.getItems().add(strategicCoefficientEntityUpdated);
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

        VerticalLayout verticalLayout = getDefaultDialogLayout("", layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}