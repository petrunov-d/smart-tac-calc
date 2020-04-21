package com.dp.trains.ui.components.dialogs.edit;

import com.dp.trains.model.dto.CarrierCompanyDto;
import com.dp.trains.model.entities.CarrierCompanyEntity;
import com.dp.trains.services.CarrierCompanyService;
import com.dp.trains.ui.components.dialogs.SmartTACCalcDialogBase;
import com.dp.trains.ui.components.grids.CarrierCompanyGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
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
@SuppressWarnings("unchecked")
public class EditCarrierCompanyDialog extends SmartTACCalcDialogBase {

    public EditCarrierCompanyDialog(CarrierCompanyGrid carrierCompanyGrid,
                                    CarrierCompanyService carrierCompanyService,
                                    CarrierCompanyEntity item) {

        super(carrierCompanyGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<CarrierCompanyDto> binder = new Binder<>();

        CarrierCompanyDto lineNumberDto = new CarrierCompanyDto();

        TextArea carrierName = new TextArea();

        carrierName.setValueChangeMode(ValueChangeMode.EAGER);
        carrierName.addValueChangeListener(event -> binder.validate());
        carrierName.setRequiredIndicatorVisible(true);

        TextArea locomotiveSeries = new TextArea();

        locomotiveSeries.setValueChangeMode(ValueChangeMode.EAGER);
        locomotiveSeries.addValueChangeListener(event -> binder.validate());
        locomotiveSeries.setRequiredIndicatorVisible(true);

        TextArea locomotiveType = new TextArea();

        locomotiveType.setValueChangeMode(ValueChangeMode.EAGER);
        locomotiveType.addValueChangeListener(event -> binder.validate());
        locomotiveType.setRequiredIndicatorVisible(true);

        NumberField locomotiveWeight = new NumberField();

        locomotiveWeight.setValueChangeMode(ValueChangeMode.EAGER);
        locomotiveWeight.addValueChangeListener(event -> binder.validate());
        locomotiveWeight.setRequiredIndicatorVisible(true);

        carrierName.setValue(item.getCarrierName());
        locomotiveSeries.setValue(item.getLocomotiveSeries());
        locomotiveType.setValue(item.getLocomotiveType());
        locomotiveWeight.setValue(item.getLocomotiveWeight());

        binder.forField(carrierName)
                .asRequired()
                .bind(CarrierCompanyDto::getCarrierName, CarrierCompanyDto::setCarrierName);

        binder.forField(locomotiveSeries)
                .bind(CarrierCompanyDto::getLocomotiveSeries, CarrierCompanyDto::setLocomotiveSeries);

        binder.forField(locomotiveType)
                .asRequired()
                .bind(CarrierCompanyDto::getLocomotiveType, CarrierCompanyDto::setLocomotiveType);

        binder.forField(locomotiveWeight)
                .asRequired()
                .bind(CarrierCompanyDto::getLocomotiveWeight, CarrierCompanyDto::setLocomotiveWeight);

        layoutWithBinder.addFormItem(carrierName, getTranslation(CALCULATE_SINGLE_PRICE_GRID_COLUMN_HEADER_NAME));
        layoutWithBinder.addFormItem(locomotiveSeries, getTranslation("Series"));
        layoutWithBinder.addFormItem(locomotiveType, getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE));
        layoutWithBinder.addFormItem(locomotiveWeight, getTranslation("Weight"));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(lineNumberDto)) {

                ListDataProvider<CarrierCompanyEntity> dataProvider =
                        (ListDataProvider<CarrierCompanyEntity>) carrierCompanyGrid.getDataProvider();

                CarrierCompanyEntity updatedLineNumberEntitiy = carrierCompanyService.update(lineNumberDto, item.getId());
                dataProvider.getItems().remove(item);
                dataProvider.getItems().add(updatedLineNumberEntitiy);

                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<CarrierCompanyDto> validate = binder.validate();

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
            carrierName.setValue("");
            locomotiveSeries.setValue("");
            locomotiveType.setValue("");
            locomotiveWeight.setValue(null);
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation(DIALOG_ADD_LINE_NUMBER_LABEL),
                layoutWithBinder, actions);

        this.add(verticalLayout);

    }
}
