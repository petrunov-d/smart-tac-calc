package com.dp.trains.ui.views;

import com.dp.trains.model.entities.*;
import com.dp.trains.ui.components.factories.AddItemDialogFactory;
import com.dp.trains.ui.components.factories.EditableDataGridFactory;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = EditDataView.NAV_EDIT_DATA_VIEW, layout = MainLayout.class)
public class EditDataView extends Composite<Div> {

    static final String NAV_EDIT_DATA_VIEW = "edit_data_view";

    @Autowired
    private EditableDataGridFactory editableDataGridFactory;

    @Autowired
    private AddItemDialogFactory addItemDialogFactory;

    private Grid currentlyActiveGrid;
    private Class<?> selectedClass;

    public EditDataView() {

        constructMenuBar();
        getContent().setHeightFull();
        EventBusHolder.getEventBus().register(this);
    }

    @PostConstruct
    public void init() {

        Grid<RailStationEntity> railStationEntityGrid = editableDataGridFactory.getRailStationsGrid();
        getContent().add(railStationEntityGrid);
        currentlyActiveGrid = railStationEntityGrid;
        selectedClass = RailStationEntity.class;

        VaadinSession session = VaadinSession.getCurrent();

        if (session.getAttribute("shouldRefreshInitially") == null ||
                session.getAttribute("shouldRefreshInitially").equals(true)) {

            UI.getCurrent().getPage().reload();

            session.setAttribute("shouldRefreshInitially", false);
        }
    }

    public void constructMenuBar() {

        final HorizontalLayout layout = new HorizontalLayout();

        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("100%");

        MenuBar menuBar = new MenuBar();

        MenuItem miRailroadStations = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_RAIL_STATIONS));

        miRailroadStations.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<RailStationEntity> railStationEntityGrid = editableDataGridFactory.getRailStationsGrid();
            getContent().add(railStationEntityGrid);
            currentlyActiveGrid = railStationEntityGrid;
            selectedClass = RailStationEntity.class;
        });

        MenuItem miSections = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SECTIONS));
        miSections.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<SectionEntity> sectionEntityGrid = editableDataGridFactory.getSectionEntityGrid();
            getContent().add(sectionEntityGrid);
            currentlyActiveGrid = sectionEntityGrid;
            selectedClass = SectionEntity.class;
        });

        MenuItem miLineTypes = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_TYPES));

        miLineTypes.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<LineTypeEntity> lineTypeEntityGrid = editableDataGridFactory.getLineTypeGrid();
            getContent().add(lineTypeEntityGrid);
            currentlyActiveGrid = lineTypeEntityGrid;
            selectedClass = LineTypeEntity.class;
        });

        MenuItem miTrainTypes = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_TRAIN_TYPES));

        miTrainTypes.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<TrainTypeEntity> trainTypeEntityGrid = editableDataGridFactory.getTrainTypeGrid();
            getContent().add(trainTypeEntityGrid);
            currentlyActiveGrid = trainTypeEntityGrid;
            selectedClass = TrainTypeEntity.class;
        });

        MenuItem miServices = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SERVICES));

        miServices.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<ServiceEntity> serviceEntityGrid = editableDataGridFactory.getServiceEntityGrid();
            getContent().add(serviceEntityGrid);
            currentlyActiveGrid = serviceEntityGrid;
            selectedClass = ServiceEntity.class;
        });

        MenuItem miLineNumbers = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_NUMBERS));

        miLineNumbers.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<LineNumberEntity> lineNumberEntityGrid = editableDataGridFactory.getLineNumberGrid();
            getContent().add(lineNumberEntityGrid);
            currentlyActiveGrid = lineNumberEntityGrid;
            selectedClass = LineNumberEntity.class;
        });

        MenuItem miStrategicCoefficients = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_STRATEGIC_COEFFICIENTS));

        miStrategicCoefficients.addClickListener(event -> {

            getContent().remove(currentlyActiveGrid);
            Grid<StrategicCoefficientEntity> strategicCoefficientsGrid =
                    editableDataGridFactory.getStrategicCoefficientsGrid();
            getContent().add(strategicCoefficientsGrid);
            currentlyActiveGrid = strategicCoefficientsGrid;
            selectedClass = StrategicCoefficientEntity.class;
        });

        layout.add(menuBar);

        Button button = new Button(new Icon(VaadinIcon.PLUS));
        button.setText(getTranslation(EDIT_DATA_VIEW_BUTTON_TEXT_ADD_ITEM));
        button.setWidth("50%");
        button.setHeight("100%");
        button.setHeightFull();
        button.addClickListener(event -> {

            log.info("Currently selected class: " + selectedClass);

            Dialog dialog = addItemDialogFactory.getDialogForClass(selectedClass, currentlyActiveGrid);

            dialog.open();
        });

        layout.add(button);

        layout.setAlignSelf(FlexComponent.Alignment.START, menuBar);
        layout.setAlignSelf(FlexComponent.Alignment.END, button);

        getContent().add(layout);
    }
}