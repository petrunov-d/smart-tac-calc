package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.LineTypeDto;
import com.dp.trains.model.entities.LineTypeEntity;
import com.dp.trains.services.LineTypeService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
public class AddLineTypeDialog extends SmartTACCalcDialogBase {

    public AddLineTypeDialog(Grid currentlyActiveGrid, LineTypeService lineTypeService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<LineTypeDto> binder = new Binder<>();

        LineTypeDto lineTypeDto = new LineTypeDto();

        TextArea lineType = new TextArea();

        lineType.setValueChangeMode(ValueChangeMode.EAGER);
        lineType.addValueChangeListener(event -> binder.validate());
        lineType.setRequiredIndicatorVisible(true);

        TextArea name = new TextArea();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);

        binder.forField(lineType)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_TYPE_INPUT_LINE_TYPE_ERROR)))
                .bind(LineTypeDto::getLineType, LineTypeDto::setLineType);

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_TYPE_INPUT_NAME_ERROR)))
                .bind(LineTypeDto::getName, LineTypeDto::setName);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(lineType, getTranslation(DIALOG_ADD_LINE_TYPE_FORM_ITEM_LABEL_LINE_TYPE));
        layoutWithBinder.addFormItem(name, getTranslation(DIALOG_ADD_LINE_TYPE_FORM_ITEM_LABEL_NAME));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(lineTypeDto)) {

                ListDataProvider<LineTypeEntity> dataProvider =
                        (ListDataProvider<LineTypeEntity>) currentlyActiveGrid.getDataProvider();

                LineTypeEntity lineTypeEntity = lineTypeService.add(lineTypeDto);
                dataProvider.getItems().add(lineTypeEntity);
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

        reset.addClickListener(event -> {

            binder.readBean(null);
            name.setValue("");
            lineType.setValue("M");
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_LINE_TYPE_TITLE), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}