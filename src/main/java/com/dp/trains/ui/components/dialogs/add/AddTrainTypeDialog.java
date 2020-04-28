package com.dp.trains.ui.components.dialogs.add;

import com.dp.trains.model.dto.TrainTypeDto;
import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.services.TrainTypeService;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.dp.trains.utils.CommonUtils;
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
public class AddTrainTypeDialog extends SmartTACCalcDialogBase {

    public AddTrainTypeDialog(Grid currentlyActiveGrid, TrainTypeService trainTypeService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<TrainTypeDto> binder = new Binder<>();

        TrainTypeDto trainTypeDto = new TrainTypeDto();

        IntegerField code = new IntegerField();

        code.setValueChangeMode(ValueChangeMode.EAGER);
        code.addValueChangeListener(event -> binder.validate());
        code.setRequiredIndicatorVisible(true);

        TextArea name = new TextArea();
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);

        binder.forField(code)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(INVALID_VALUE_FOR_CODE)))
                .bind(x -> CommonUtils.valueOf2(x.getCode()), (x, y) -> {
                    if (y == null) {
                        x.setCode(null);
                    } else {
                        x.setCode(String.valueOf(y));
                    }
                });
        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredVarcharStringValidator(getTranslation(DIALOG_ADD_LINE_TYPE_INPUT_NAME_ERROR)))
                .bind(TrainTypeDto::getName, TrainTypeDto::setName);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(code, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE));
        layoutWithBinder.addFormItem(name, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(trainTypeDto)) {

                ListDataProvider<TrainTypeEntity> dataProvider =
                        (ListDataProvider<TrainTypeEntity>) currentlyActiveGrid.getDataProvider();

                TrainTypeEntity trainTypeEntity = trainTypeService.add(trainTypeDto);
                dataProvider.getItems().add(trainTypeEntity);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<TrainTypeDto> validate = binder.validate();

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
            this.close();
        });

        reset.addClickListener(event -> {

            binder.readBean(null);
            code.setValue(null);
            name.setValue("");
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_TRAIN_TYPE_TITLE), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}