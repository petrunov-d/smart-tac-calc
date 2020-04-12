package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.ServiceChargesPerTrainDto;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.services.ServiceChargesPerTrainService;
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
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class EditServiceChargesPerTrainDialog extends SmartTACCalcDialogBase {

    public EditServiceChargesPerTrainDialog(Grid currentlyActiveGrid,
                                            ServiceChargesPerTrainService serviceChargesPerTrainService,
                                            ServiceChargesPerTrainEntity item) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<ServiceChargesPerTrainDto> binder = new Binder<>();

        ServiceChargesPerTrainDto serviceDto = new ServiceChargesPerTrainDto();

        IntegerField serviceCount = new IntegerField();
        serviceCount.addValueChangeListener(event -> binder.validate());
        serviceCount.setRequiredIndicatorVisible(true);
        serviceCount.setValue(item.getServiceCount());

        TextArea station = new TextArea();

        station.setEnabled(false);
        station.setValue(item.getRailStationEntity().getStation() == null ? "" : item.getRailStationEntity().getStation());

        TextArea service = new TextArea();

        service.setEnabled(false);
        station.setValue(item.getServiceEntity().getName() == null ? "" : item.getServiceEntity().getName());

        IntegerField trainNumber = new IntegerField();
        trainNumber.setEnabled(false);
        trainNumber.setValue(item.getTrainNumber());

        binder.forField(serviceCount)
                .asRequired()
                .bind(ServiceChargesPerTrainDto::getServiceCount, ServiceChargesPerTrainDto::setServiceCount);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(trainNumber, getTranslation("Train Number"));
        layoutWithBinder.addFormItem(station, getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION));
        layoutWithBinder.addFormItem(service, getTranslation("Service"));
        layoutWithBinder.addFormItem(serviceCount, getTranslation("Service Count"));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(serviceDto)) {

                ListDataProvider<ServiceChargesPerTrainEntity> dataProvider =
                        (ListDataProvider<ServiceChargesPerTrainEntity>) currentlyActiveGrid.getDataProvider();

                ServiceChargesPerTrainEntity serviceEntityUpdated = serviceChargesPerTrainService.update(serviceDto, item.getId());
                dataProvider.getItems().remove(item);
                dataProvider.getItems().add(serviceEntityUpdated);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<ServiceChargesPerTrainDto> validate = binder.validate();

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
            serviceCount.setValue(null);
            station.setValue("");
            service.setValue("");
            trainNumber.setValue(null);
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout("", layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
