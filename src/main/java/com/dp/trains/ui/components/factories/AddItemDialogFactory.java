package com.dp.trains.ui.components.factories;

import com.dp.trains.model.entities.*;
import com.dp.trains.services.*;
import com.dp.trains.ui.components.dialogs.add.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddItemDialogFactory {

    private final ServiceService serviceService;
    private final LineTypeService lineTypeService;
    private final SectionsService sectionsService;
    private final TrainTypeService trainTypeService;
    private final LineNumberService lineNumberService;
    private final RailStationService railStationService;
    private final CarrierCompanyService carrierCompanyService;
    private final MarkupCoefficientService markupCoefficientService;
    private final StrategicCoefficientService strategicCoefficientService;

    public Dialog getDialogForClass(Class<?> clazz, Grid currentlyActiveGrid) {

        if (clazz == StrategicCoefficientEntity.class) {

            return new AddStrategicCoefficientDialog(currentlyActiveGrid, strategicCoefficientService);

        } else if (clazz == TrainTypeEntity.class) {

            return new AddTrainTypeDialog(currentlyActiveGrid, trainTypeService);

        } else if (clazz == ServiceEntity.class) {

            return new AddServiceDialog(currentlyActiveGrid, serviceService);

        } else if (clazz == LineTypeEntity.class) {

            return new AddLineTypeDialog(currentlyActiveGrid, lineTypeService);

        } else if (clazz == RailStationEntity.class) {

            return new AddRailStationDialog(currentlyActiveGrid, railStationService, lineNumberService);

        } else if (clazz == SectionEntity.class) {

            return new AddSectionDialog(currentlyActiveGrid, sectionsService, lineTypeService, lineNumberService, railStationService);
        } else if (clazz == LineNumberEntity.class) {

            return new AddLineNumberDialog(currentlyActiveGrid, lineNumberService);
        } else if (clazz == MarkupCoefficientEntity.class) {

            return new AddMarkupCoefficientDialog(currentlyActiveGrid, markupCoefficientService);

        } else if (clazz == CarrierCompanyEntity.class) {

            return new AddCarrierCompanyDialog(currentlyActiveGrid, carrierCompanyService);
        }

        return null;
    }
}