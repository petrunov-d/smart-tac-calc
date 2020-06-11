package com.dp.trains.ui.components.dialogs.edit;

import com.dp.trains.model.dto.LineTypeDto;
import com.dp.trains.model.entities.LineTypeEntity;
import com.dp.trains.services.LineTypeService;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
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
public class EditLineTypeDialog extends SmartTACCalcDialogBase {

    public EditLineTypeDialog(Grid currentlyActiveGrid, LineTypeService lineTypeService, LineTypeEntity lineTypeEntity) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<LineTypeDto> binder = new Binder<>();

        LineTypeDto lineTypeDto = new LineTypeDto();

        Select<String> lineType = new Select<>();
        lineType.setItems(lineTypeService.getLineTypes());
        lineType.setValue(lineTypeEntity.getLineType());

        TextArea name = new TextArea();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);
        name.setValue(lineTypeEntity.getName() == null ? "" : lineTypeEntity.getName());

        TextArea code = new TextArea();

        code.setValueChangeMode(ValueChangeMode.EAGER);
        code.addValueChangeListener(event -> binder.validate());
        code.setRequiredIndicatorVisible(true);

        binder.forField(lineType)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_TYPE_INPUT_LINE_TYPE_ERROR)))
                .bind(LineTypeDto::getLineType, LineTypeDto::setLineType);

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_TYPE_INPUT_NAME_ERROR)))
                .bind(LineTypeDto::getName, LineTypeDto::setName);

        binder.forField(code)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GENERAL_ERROR_HINT_CODE_NOT_SET)))
                .bind(LineTypeDto::getCode, LineTypeDto::setCode);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(lineType, getTranslation(DIALOG_ADD_LINE_TYPE_FORM_ITEM_LABEL_LINE_TYPE));
        layoutWithBinder.addFormItem(name, getTranslation(DIALOG_ADD_LINE_TYPE_FORM_ITEM_LABEL_NAME));
        layoutWithBinder.addFormItem(code, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(lineTypeDto)) {

                ListDataProvider<LineTypeEntity> dataProvider =
                        (ListDataProvider<LineTypeEntity>) currentlyActiveGrid.getDataProvider();

                LineTypeEntity lineTypeEntityUpdated = lineTypeService.update(lineTypeDto, lineTypeEntity.getId());
                dataProvider.getItems().remove(lineTypeEntity);
                dataProvider.getItems().add(lineTypeEntityUpdated);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<LineTypeDto> validate = binder.validate();

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
            name.setValue("");
            lineType.setValue("M");
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout("", layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}