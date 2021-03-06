package com.dp.trains.ui.components.dialogs.edit;

import com.dp.trains.model.dto.SubSectionDto;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.services.RailStationService;
import com.dp.trains.services.SubSectionService;
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
import com.vaadin.flow.component.textfield.NumberField;
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
public class EditSubSectionDialog extends SmartTACCalcDialogBase {

    public EditSubSectionDialog(Grid currentlyActiveGrid, SubSectionService subSectionService, SubSectionEntity subSectionEntity,
                                RailStationService railStationService) {

        super(currentlyActiveGrid);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<SubSectionDto> binder = new Binder<>();

        SubSectionDto subSectionDto = new SubSectionDto();

        NumberField kilometers = new NumberField();

        kilometers.setValueChangeMode(ValueChangeMode.EAGER);
        kilometers.addValueChangeListener(event -> binder.validate());
        kilometers.setRequiredIndicatorVisible(true);
        kilometers.setValue(subSectionEntity.getKilometers());

        Select<String> nonKeyStation = new Select<>();

        nonKeyStation.addValueChangeListener(event -> binder.validate());
        nonKeyStation.setRequiredIndicatorVisible(true);
        nonKeyStation.setItems(railStationService.getAllStationNames(false));

        binder.forField(kilometers)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(DIALOG_ADD_SUB_SECTION_FORM_ITEM_KILOMETERS_ERROR)))
                .bind(SubSectionDto::getKilometers, SubSectionDto::setKilometers);

        binder.forField(nonKeyStation)
                .asRequired()
                .withValidator(ValidatorFactory.requiredVarcharStringValidator(getTranslation(DIALOG_ADD_SUB_SECTION_FORM_ITEM_NON_KEY_STATION_ERROR)))
                .bind(SubSectionDto::getNonKeyStation, SubSectionDto::setNonKeyStation);

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        layoutWithBinder.addFormItem(kilometers, getTranslation(DIALOG_ADD_SUB_SECTION_FORM_ITEM_KILOMETERS));
        layoutWithBinder.addFormItem(nonKeyStation, getTranslation(DIALOG_ADD_SUB_SECTION_FORM_ITEM_NON_KEY_STATION));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(subSectionDto)) {

                ListDataProvider<SubSectionEntity> dataProvider =
                        (ListDataProvider<SubSectionEntity>) currentlyActiveGrid.getDataProvider();

                SubSectionEntity subSectionEntityUpdated = subSectionService.update(subSectionDto, subSectionEntity.getId());
                dataProvider.getItems().remove(subSectionEntity);
                dataProvider.getItems().add(subSectionEntityUpdated);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<SubSectionDto> validate = binder.validate();

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
            kilometers.setValue(null);
            nonKeyStation.setValue("");
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout("", layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
