package com.dp.trains.ui.views;

import com.dp.trains.model.entities.RailStationEntity;
import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.model.entities.user.UserAccess;
import com.dp.trains.services.RailStationService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.services.ServiceService;
import com.dp.trains.ui.components.common.UserPermissionAwareView;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.google.common.base.Joiner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ServiceChargesForTrainNumberView.NAV_ADD_SERVICE_CHARGE_FOR_TRAIN_NUMBER, layout = MainLayout.class)
public class ServiceChargesForTrainNumberView extends UserPermissionAwareView {

    static final String NAV_ADD_SERVICE_CHARGE_FOR_TRAIN_NUMBER = "add_service_charge_for_train";

    @Autowired
    private RailStationService railStationService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceChargesPerTrainService serviceChargesPerTrainService;

    private final Select<RailStationEntity> railStationEntitySelect;
    private final Select<ServiceEntity> serviceEntitySelect;
    private final IntegerField trainNumber;
    private final IntegerField serviceCount;

    public ServiceChargesForTrainNumberView() {

        super();

        VerticalLayout verticalLayout = new VerticalLayout();

        this.railStationEntitySelect = new Select<>();
        this.railStationEntitySelect.setRequiredIndicatorVisible(true);
        this.serviceEntitySelect = new Select<>();
        this.serviceEntitySelect.setRequiredIndicatorVisible(true);
        this.trainNumber = new IntegerField();
        this.trainNumber.setRequiredIndicatorVisible(true);

        this.serviceCount = new IntegerField();
        this.serviceCount.setRequiredIndicatorVisible(true);

        H2 h2 = new H2(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_PAGE_TITLE));

        HorizontalLayout railStationSelectorLayout = new HorizontalLayout();
        railStationSelectorLayout.add(new H5(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_SELECT_RAIL_STATION)),
                railStationEntitySelect);

        HorizontalLayout serviceEntitySelectorLayout = new HorizontalLayout();
        serviceEntitySelectorLayout.add(new H5(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_SELECT_SERVICE)),
                serviceEntitySelect);

        HorizontalLayout trainNumberLayout = new HorizontalLayout();
        trainNumberLayout.add(new H5(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_INPUT_TRAIN_NUMBER)), trainNumber);

        HorizontalLayout serviceCountLayout = new HorizontalLayout();
        serviceCountLayout.add(new H5(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_INPUT_SERVICE_COUNT)), serviceCount);

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button reset = new Button(getTranslation(SHARED_BUTTON_TEXT_RESET), new Icon(VaadinIcon.RECYCLE));

        save.addClickListener(event -> {

            List<String> validationErrors = getValidation();

            if (validationErrors.size() > 0) {

                Dialog basicInfoDialog = new BasicInfoDialog(
                        getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_MANDATORY_VALUES_MESSAGE)
                                + Joiner.on(", ").join(validationErrors));

                basicInfoDialog.open();

            } else {

                ServiceChargesPerTrainEntity serviceChargesPerTrainEntity = new ServiceChargesPerTrainEntity();
                serviceChargesPerTrainEntity.setRailStationEntity(railStationEntitySelect.getValue());
                serviceChargesPerTrainEntity.setServiceEntity(serviceEntitySelect.getValue());
                serviceChargesPerTrainEntity.setServiceCount(serviceCount.getValue());
                serviceChargesPerTrainEntity.setTrainNumber(trainNumber.getValue());

                serviceChargesPerTrainService.save(serviceChargesPerTrainEntity);
                reset();
            }
        });

        reset.addClickListener(event -> reset());

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(headerText);
        verticalLayout.add(h2);
        verticalLayout.add(trainNumberLayout);
        verticalLayout.add(railStationSelectorLayout);
        verticalLayout.add(serviceEntitySelectorLayout);
        verticalLayout.add(serviceCountLayout);
        verticalLayout.add(new HorizontalLayout(save, reset));

        add(verticalLayout, getFooter());
    }

    @Override
    public UserAccess getViewUserAccessPermission() {
        return UserAccess.ADD_SERVICE_CHARGE_FOR_TRAIN;
    }

    private void reset() {

        this.railStationEntitySelect.setValue(null);
        this.serviceEntitySelect.setValue(null);
        this.trainNumber.setValue(null);
        this.serviceCount.setValue(null);
    }

    private List<String> getValidation() {

        List<String> validationList = Lists.newArrayList();

        if (railStationEntitySelect.getValue() == null) {

            validationList.add(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_VALIDATION_RAIL_STATION));
        }

        if (serviceEntitySelect.getValue() == null) {

            validationList.add(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_VALIDATION_SERVICE));
        }

        if (trainNumber.getValue() == null) {

            validationList.add(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_VALIDATION_TRAIN_NUMBER));
        }

        if (serviceCount.getValue() == null) {

            validationList.add(getTranslation(ADD_SERVICE_CHARGES_PER_TRAIN_VALIDATION_SERVICE_COUNT));
        }

        return validationList;
    }

    @PostConstruct
    public void init() {

        this.railStationEntitySelect.setItems(this.railStationService.fetch(0, 0));
        this.railStationEntitySelect.setItemLabelGenerator(RailStationEntity::getStation);

        this.serviceEntitySelect.setItems(serviceService.fetch(0, 0));
        this.serviceEntitySelect.setItemLabelGenerator(ServiceEntity::getName);
    }
}