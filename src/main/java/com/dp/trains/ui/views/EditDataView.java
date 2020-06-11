package com.dp.trains.ui.views;

import com.dp.trains.event.SmartTACCalcNeedsRefreshEvent;
import com.dp.trains.model.entities.*;
import com.dp.trains.model.entities.user.UserAccess;
import com.dp.trains.ui.components.common.UserPermissionAwareView;
import com.dp.trains.ui.components.factories.AddItemDialogFactory;
import com.dp.trains.ui.components.factories.EditableDataGridFactory;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
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
public class EditDataView extends UserPermissionAwareView {

    static final String NAV_EDIT_DATA_VIEW = "edit_data_view";

    @Autowired
    private EditableDataGridFactory editableDataGridFactory;

    @Autowired
    private AddItemDialogFactory addItemDialogFactory;

    private Grid currentlyActiveGrid;
    private Class<?> selectedClass;

    public EditDataView() {

        super();

        constructMenuBar();
        this.setHeightFull();
        EventBusHolder.getEventBus().register(this);
    }

    @Override
    public UserAccess getViewUserAccessPermission() {
        return UserAccess.EDIT_DATA;
    }

    @PostConstruct
    public void init() {

        Grid<RailStationEntity> railStationEntityGrid = editableDataGridFactory.getRailStationsGrid();
        this.add(railStationEntityGrid);
        currentlyActiveGrid = railStationEntityGrid;
        selectedClass = RailStationEntity.class;
    }

    @Subscribe
    public void listenForRefresh(SmartTACCalcNeedsRefreshEvent smartTACCalcNeedsRefreshEvent) {

        UI.getCurrent().getPage().reload();
    }

    public void constructMenuBar() {

        final HorizontalLayout layout = new HorizontalLayout();

        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("100%");

        MenuBar menuBar = new MenuBar();

        MenuItem miRailroadStations = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_RAIL_STATIONS));

        miRailroadStations.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<RailStationEntity> railStationEntityGrid = editableDataGridFactory.getRailStationsGrid();
            this.add(railStationEntityGrid);
            currentlyActiveGrid = railStationEntityGrid;
            selectedClass = RailStationEntity.class;
        });

        MenuItem miSections = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SECTIONS));
        miSections.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<SectionEntity> sectionEntityGrid = editableDataGridFactory.getSectionEntityGrid();
            this.add(sectionEntityGrid);
            currentlyActiveGrid = sectionEntityGrid;
            selectedClass = SectionEntity.class;
        });

        MenuItem miLineTypes = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_TYPES));

        miLineTypes.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<LineTypeEntity> lineTypeEntityGrid = editableDataGridFactory.getLineTypeGrid();
            this.add(lineTypeEntityGrid);
            currentlyActiveGrid = lineTypeEntityGrid;
            selectedClass = LineTypeEntity.class;
        });

        MenuItem miTrainTypes = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_TRAIN_TYPES));

        miTrainTypes.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<TrainTypeEntity> trainTypeEntityGrid = editableDataGridFactory.getTrainTypeGrid();
            this.add(trainTypeEntityGrid);
            currentlyActiveGrid = trainTypeEntityGrid;
            selectedClass = TrainTypeEntity.class;
        });

        MenuItem miServices = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SERVICES));

        miServices.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<ServiceEntity> serviceEntityGrid = editableDataGridFactory.getServiceEntityGrid();
            this.add(serviceEntityGrid);
            currentlyActiveGrid = serviceEntityGrid;
            selectedClass = ServiceEntity.class;
        });

        MenuItem miLineNumbers = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_NUMBERS));

        miLineNumbers.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<LineNumberEntity> lineNumberEntityGrid = editableDataGridFactory.getLineNumberGrid();
            this.add(lineNumberEntityGrid);
            currentlyActiveGrid = lineNumberEntityGrid;
            selectedClass = LineNumberEntity.class;
        });

        MenuItem miStrategicCoefficients = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_STRATEGIC_COEFFICIENTS));

        miStrategicCoefficients.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<StrategicCoefficientEntity> strategicCoefficientsGrid = editableDataGridFactory.getStrategicCoefficientsGrid();
            this.add(strategicCoefficientsGrid);
            currentlyActiveGrid = strategicCoefficientsGrid;
            selectedClass = StrategicCoefficientEntity.class;
        });

        MenuItem miMarkupCoefficients = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_MARKUP_COEFFICIENT));

        miMarkupCoefficients.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<MarkupCoefficientEntity> markupCoefficientEntityGrid = editableDataGridFactory.getMarkupCoefficientsGrid();
            this.add(markupCoefficientEntityGrid);
            currentlyActiveGrid = markupCoefficientEntityGrid;
            selectedClass = MarkupCoefficientEntity.class;
        });

        MenuItem miCarrierCompany = menuBar.addItem(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_CARRIER_COMPANY));

        miCarrierCompany.addClickListener(event -> {

            this.remove(currentlyActiveGrid);
            Grid<CarrierCompanyEntity> carrierCompanyEntityGrid = editableDataGridFactory.getCarrierCompanyGrid();
            this.add(carrierCompanyEntityGrid);
            currentlyActiveGrid = carrierCompanyEntityGrid;
            selectedClass = CarrierCompanyEntity.class;
        });


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
        layout.add(menuBar);
        layout.setAlignSelf(FlexComponent.Alignment.START, button);
        layout.setAlignSelf(FlexComponent.Alignment.END, menuBar);

        this.add(layout);
    }
}