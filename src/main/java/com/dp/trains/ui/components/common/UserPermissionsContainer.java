package com.dp.trains.ui.components.common;

import com.dp.trains.model.entities.user.UserAccess;
import com.google.common.collect.Sets;
import com.vaadin.flow.component.checkbox.Checkbox;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Set;

@Slf4j
public class UserPermissionsContainer extends BaseSmartTacCalcView {

    private final Checkbox calculatePricePerTrainViewAccess;
    private final Checkbox calculateSinglePriceViewAccess;
    private final Checkbox editDataViewAccess;
    private final Checkbox importDataViewAccess;
    private final Checkbox reportsViewAccess;
    private final Checkbox serviceChargesForTrainNumberViewAccess;
    private final Checkbox viewSinglePriceViewAcccess;

    public UserPermissionsContainer(Collection<UserAccess> userAccesses) {

        super();

        calculatePricePerTrainViewAccess = new Checkbox("Access Calculate Price Per Train");
        calculateSinglePriceViewAccess = new Checkbox("Access Calculate Single Price");
        editDataViewAccess = new Checkbox("Access Data Editing");
        importDataViewAccess = new Checkbox("Access Data Importing");
        reportsViewAccess = new Checkbox("Access Reports");
        serviceChargesForTrainNumberViewAccess = new Checkbox("Access Service Charges For Train");
        viewSinglePriceViewAcccess = new Checkbox("Access View Single Price");

        if (!(userAccesses == null) && (!userAccesses.isEmpty())) {

            if (userAccesses.contains(UserAccess.CALCULATE_PRICE_PER_TRAIN)) {

                calculatePricePerTrainViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.CALCULATE_SINGLE_PRICE)) {

                calculateSinglePriceViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.EDIT_DATA)) {

                editDataViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.IMPORT_DATA)) {

                importDataViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.REPORTS)) {

                reportsViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.ADD_SERVICE_CHARGE_FOR_TRAIN)) {

                serviceChargesForTrainNumberViewAccess.setValue(true);
            }

            if (userAccesses.contains(UserAccess.VIEW_SINGLE_PRICE)) {

                viewSinglePriceViewAcccess.setValue(true);
            }
        }

        this.add(calculatePricePerTrainViewAccess, calculateSinglePriceViewAccess, calculatePricePerTrainViewAccess,
                editDataViewAccess, importDataViewAccess, reportsViewAccess, serviceChargesForTrainNumberViewAccess,
                viewSinglePriceViewAcccess);
    }

    public Set<UserAccess> getData() {

        Set<UserAccess> newAccesses = Sets.newHashSet();

        if (calculatePricePerTrainViewAccess.getValue()) {
            newAccesses.add(UserAccess.CALCULATE_PRICE_PER_TRAIN);
        }
        if (calculateSinglePriceViewAccess.getValue()) {
            newAccesses.add(UserAccess.CALCULATE_SINGLE_PRICE);
        }
        if (editDataViewAccess.getValue()) {
            newAccesses.add(UserAccess.EDIT_DATA);
        }
        if (importDataViewAccess.getValue()) {
            newAccesses.add(UserAccess.IMPORT_DATA);
        }
        if (reportsViewAccess.getValue()) {
            newAccesses.add(UserAccess.REPORTS);
        }
        if (serviceChargesForTrainNumberViewAccess.getValue()) {
            newAccesses.add(UserAccess.ADD_SERVICE_CHARGE_FOR_TRAIN);
        }

        if (viewSinglePriceViewAcccess.getValue()) {
            newAccesses.add(UserAccess.ADD_SERVICE_CHARGE_FOR_TRAIN);
        }

        return newAccesses;
    }

    public void reset() {

        this.calculatePricePerTrainViewAccess.setValue(false);
        this.calculateSinglePriceViewAccess.setValue(false);
        this.editDataViewAccess.setValue(false);
        this.importDataViewAccess.setValue(false);
        this.reportsViewAccess.setValue(false);
        this.serviceChargesForTrainNumberViewAccess.setValue(false);
        this.viewSinglePriceViewAcccess.setValue(false);
    }
}