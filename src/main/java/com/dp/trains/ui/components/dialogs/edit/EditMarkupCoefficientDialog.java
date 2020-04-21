package com.dp.trains.ui.components.dialogs.edit;

import com.dp.trains.model.dto.MarkupCoefficientDto;
import com.dp.trains.model.entities.MarkupCoefficientEntity;
import com.dp.trains.services.MarkupCoefficientService;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
@SuppressWarnings({"rawtypes", "unchecked"})
public class EditMarkupCoefficientDialog extends SmartTACCalcDialogBase {

    public EditMarkupCoefficientDialog(Grid markupCoefficientGrid,
                                       MarkupCoefficientService markupCoefficientService,
                                       MarkupCoefficientEntity item) {

        super(markupCoefficientGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<MarkupCoefficientDto> binder = new Binder<>();

        MarkupCoefficientDto markupCoefficientDto = new MarkupCoefficientDto();

        TextArea name = new TextArea();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);

        TextArea code = new TextArea();

        code.setValueChangeMode(ValueChangeMode.EAGER);
        code.addValueChangeListener(event -> binder.validate());
        code.setRequiredIndicatorVisible(true);

        NumberField coefficient = new NumberField();

        coefficient.setValueChangeMode(ValueChangeMode.EAGER);
        coefficient.addValueChangeListener(event -> binder.validate());
        coefficient.setRequiredIndicatorVisible(true);

        name.setValue(item.getName());
        code.setValue(item.getCode());
        coefficient.setValue(item.getCoefficient());

        binder.forField(name)
                .asRequired()
                .bind(MarkupCoefficientDto::getName, MarkupCoefficientDto::setName);

        binder.forField(code)
                .bind(MarkupCoefficientDto::getCode, MarkupCoefficientDto::setCode);

        binder.forField(coefficient)
                .asRequired()
                .bind(MarkupCoefficientDto::getCoefficient, MarkupCoefficientDto::setCoefficient);

        layoutWithBinder.addFormItem(name, getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(code, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE));
        layoutWithBinder.addFormItem(coefficient, getTranslation(GRID_STRATEGIC_COEFFICIENT_COLUMN_HEADER_COEFFICIENT));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(markupCoefficientDto)) {

                ListDataProvider<MarkupCoefficientEntity> dataProvider =
                        (ListDataProvider<MarkupCoefficientEntity>) markupCoefficientGrid.getDataProvider();

                MarkupCoefficientEntity updatedLineNumberEntitiy = markupCoefficientService.update(markupCoefficientDto, item.getId());
                dataProvider.getItems().remove(item);
                dataProvider.getItems().add(updatedLineNumberEntitiy);

                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<MarkupCoefficientDto> validate = binder.validate();

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
            code.setValue("");
            coefficient.setValue(null);
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_LINE_NUMBER_LABEL),
                layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}