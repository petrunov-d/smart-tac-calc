package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.ServiceDto;
import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.ServiceService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
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
public class AddServiceDialog extends AddDialogBase {

    public AddServiceDialog(Grid currentlyActiveGrid, ServiceService serviceService, LineNumberService lineNumberService) {
        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<ServiceDto> binder = new Binder<>();

        ServiceDto serviceDto = new ServiceDto();

        ComboBox<Integer> lineNumber = new ComboBox<>();

        lineNumber.setItems(lineNumberService.getLineNumbersAsInts());
        lineNumber.addValueChangeListener(event -> binder.validate());
        lineNumber.setRequiredIndicatorVisible(true);

        TextField metric = new TextField();

        metric.setValueChangeMode(ValueChangeMode.EAGER);
        metric.addValueChangeListener(event -> binder.validate());
        metric.setRequiredIndicatorVisible(true);

        NumberField unitPrice = new NumberField();

        unitPrice.setValueChangeMode(ValueChangeMode.EAGER);
        unitPrice.addValueChangeListener(event -> binder.validate());
        unitPrice.setRequiredIndicatorVisible(true);

        TextField name = new TextField();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);

        ComboBox<String> serviceType = new ComboBox<>();
        serviceType.setItems(serviceService.getServiceTypes());
        serviceType.setClearButtonVisible(true);

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(ServiceDto::getLineNumber, ServiceDto::setLineNumber);

        binder.forField(metric)
                .asRequired()
                .withValidator(ValidatorFactory.requiredVarcharStringValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_METRIC)))
                .bind(ServiceDto::getMetric, ServiceDto::setMetric);

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .bind(ServiceDto::getMetric, ServiceDto::setMetric);

        binder.forField(unitPrice)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_UNIT_PRICE)))
                .bind(ServiceDto::getUnitPrice, ServiceDto::setUnitPrice);

        binder.forField(serviceType)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_SERVICE_FORM_ITEM_SERVICE_TYPE_VALIDATION)))
                .bind(ServiceDto::getType, ServiceDto::setType);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(lineNumber, getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        layoutWithBinder.addFormItem(name, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(metric, getTranslation(GRID_SERVICE_COLUMN_HEADER_METRIC));
        layoutWithBinder.addFormItem(unitPrice, getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE));
        layoutWithBinder.addFormItem(serviceType, getTranslation(DIALOG_ADD_SERVICE_FORM_ITEM_SERVICE_TYPE));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(serviceDto)) {

                ListDataProvider<ServiceEntity> dataProvider =
                        (ListDataProvider<ServiceEntity>) currentlyActiveGrid.getDataProvider();

                ServiceEntity serviceEntity = serviceService.add(serviceDto);
                dataProvider.getItems().add(serviceEntity);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<ServiceDto> validate = binder.validate();

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
            metric.setValue("");
            unitPrice.setValue(null);
            name.setValue("");
            serviceType.setValue("");
            this.close();
        });

        reset.addClickListener(event -> {

            binder.readBean(null);
            lineNumber.setValue(null);
            metric.setValue("");
            unitPrice.setValue(null);
            name.setValue("");
            serviceType.setValue("");
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_SERVICE_TITLE), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
