package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.ServiceDto;
import com.dp.trains.model.entities.ServiceEntity;
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
public class EditServiceDialog extends AddDialogBase {

    public EditServiceDialog(Grid currentlyActiveGrid, ServiceService serviceService, ServiceEntity serviceEntity) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<ServiceDto> binder = new Binder<>();

        ServiceDto serviceDto = new ServiceDto();

        TextField metric = new TextField();

        metric.setValueChangeMode(ValueChangeMode.EAGER);
        metric.addValueChangeListener(event -> binder.validate());
        metric.setRequiredIndicatorVisible(true);
        metric.setValue(serviceEntity.getMetric());

        NumberField unitPrice = new NumberField();

        unitPrice.setValueChangeMode(ValueChangeMode.EAGER);
        unitPrice.addValueChangeListener(event -> binder.validate());
        unitPrice.setRequiredIndicatorVisible(true);
        unitPrice.setValue(serviceEntity.getUnitPrice());

        TextField name = new TextField();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.addValueChangeListener(event -> binder.validate());
        name.setRequiredIndicatorVisible(true);
        name.setValue(serviceEntity.getName());

        ComboBox<String> serviceType = new ComboBox<>();
        serviceType.setItems(serviceService.getServiceTypes());
        serviceType.setClearButtonVisible(true);
        serviceType.setValue(serviceEntity.getType());

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
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(name, getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(metric, getTranslation(GRID_SERVICE_COLUMN_HEADER_METRIC));
        layoutWithBinder.addFormItem(unitPrice, getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE));
        layoutWithBinder.addFormItem(serviceType, getTranslation(DIALOG_ADD_SERVICE_FORM_ITEM_SERVICE_TYPE));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(serviceDto)) {

                ListDataProvider<ServiceEntity> dataProvider =
                        (ListDataProvider<ServiceEntity>) currentlyActiveGrid.getDataProvider();

                ServiceEntity serviceEntityUpdated = serviceService.update(serviceDto, serviceEntity.getId());
                dataProvider.getItems().remove(serviceEntity);
                dataProvider.getItems().add(serviceEntityUpdated);
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
            metric.setValue("");
            unitPrice.setValue(null);
            name.setValue("");
            serviceType.setValue("");
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout("", layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
