package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.LineNumberDto;
import com.dp.trains.model.entities.LineNumberEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
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
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class EditLineNumberDialog extends SmartTACCalcDialogBase {

    public EditLineNumberDialog(Grid currentlyActiveGrid, LineNumberService lineNumberService,
                                LineNumberEntity lineNumberEntity) {

        super(currentlyActiveGrid);

        Binder<LineNumberDto> binder = new Binder<>(LineNumberDto.class);

        LineNumberDto lineNumberDto = new LineNumberDto();

        IntegerField lineNumber = new IntegerField();
        lineNumber.addValueChangeListener(event -> binder.validate());
        lineNumber.setRequiredIndicatorVisible(true);

        TextArea description = new TextArea();
        description.addValueChangeListener(event -> binder.validate());
        description.setRequiredIndicatorVisible(true);

        lineNumber.setValue(lineNumberEntity.getLineNumber());
        description.setValue(lineNumberEntity.getDescription() == null ? "" : lineNumberEntity.getDescription());

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(LineNumberDto::getLineNumber, LineNumberDto::setLineNumber);

        binder.forField(description)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_LINE_NUMBER_FORM_ITEM_DESCRIPTION_VALIDATION)))
                .withStatusLabel(new Label(getTranslation(DIALOG_ADD_LINE_NUMBER_FORM_ITEM_DESCRIPTION_VALIDATION)))
                .bind(LineNumberDto::getDescription, LineNumberDto::setDescription);

        FormLayout layoutWithBinder = new FormLayout();

        layoutWithBinder.addFormItem(lineNumber, getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        layoutWithBinder.addFormItem(description, getTranslation(DIALOG_ADD_LINE_NUMBER_FORM_ITEM_DESCRIPTION));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(lineNumberDto)) {

                ListDataProvider<LineNumberEntity> dataProvider =
                        (ListDataProvider<LineNumberEntity>) currentlyActiveGrid.getDataProvider();

                LineNumberEntity updatedLineNumberEntitiy = lineNumberService.update(lineNumberDto, lineNumberEntity.getId());
                dataProvider.getItems().remove(lineNumberEntity);
                dataProvider.getItems().add(updatedLineNumberEntitiy);

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

        VerticalLayout verticalLayout = getDefaultDialogLayout("",
                layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}