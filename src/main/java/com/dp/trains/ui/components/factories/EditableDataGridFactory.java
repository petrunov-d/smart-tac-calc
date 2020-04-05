package com.dp.trains.ui.components.factories;

import com.dp.trains.model.entities.*;
import com.dp.trains.services.*;
import com.dp.trains.ui.components.grids.*;
import com.vaadin.flow.component.grid.Grid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class EditableDataGridFactory {

    private final ServiceService serviceService;
    private final LineTypeService lineTypeService;
    private final SectionsService sectionsService;
    private final TrainTypeService trainTypeService;
    private final SubSectionService subSectionService;
    private final LineNumberService lineNumberService;
    private final RailStationService railStationService;
    private final TrainsUserDetailService trainsUserDetailService;
    private final StrategicCoefficientService strategicCoefficientService;

    public Grid<TrainTypeEntity> getTrainTypeGrid() {

        return new TrainTypeGrid(trainTypeService);
    }

    public Grid<ServiceEntity> getServiceEntityGrid() {

        return new ServiceGrid(serviceService, lineNumberService);
    }

    public Grid<LineTypeEntity> getLineTypeGrid() {

        return new LineTypeGrid(lineTypeService);
    }

    public Grid<RailStationEntity> getRailStationsGrid() {

        return new RailStationsGrid(railStationService, lineNumberService);
    }

    public Grid<StrategicCoefficientEntity> getStrategicCoefficientsGrid() {

        return new StrategicCoefficientsGrid(strategicCoefficientService);
    }

    public Grid<UserEntity> getUsersGrid() {

        return new UserManagementGrid(trainsUserDetailService);
    }

    public Grid<SectionEntity> getSectionEntityGrid() {

        return new SectionGrid(sectionsService, subSectionService, lineNumberService, lineTypeService, railStationService);
    }

    public Grid<LineNumberEntity> getLineNumberGrid() {

        return new LineNumberGrid(lineNumberService);
    }
}