package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.ServiceChargesPerTrainEntity;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ServiceChargesPerTrainGrid extends SmartTACCalcGrid<ServiceChargesPerTrainEntity> {

    public ServiceChargesPerTrainGrid(ServiceChargesPerTrainService serviceChargesPerTrainService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(serviceChargesPerTrainService.fetch(0, 0)));

        Grid.Column<ServiceChargesPerTrainEntity> trainNumberColumn = this.addColumn(ServiceChargesPerTrainEntity::getTrainNumber)
                .setHeader(getTranslation("TrainNumber"))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> stationColumn = this.addColumn(s -> s.getRailStationEntity().getStation())
                .setHeader(getTranslation(GRID_RAIL_STATION_COLUMN_HEADER_STATION))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> serviceNameColumn = this.addColumn(s -> s.getServiceEntity().getName())
                .setHeader(getTranslation("Service"))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceChargesPerTrainEntity> serviceCountColumn = this.addColumn(ServiceChargesPerTrainEntity::getServiceCount)
                .setHeader(getTranslation("Service Count"))
                .setSortable(true)
                .setResizable(true);


        Binder<ServiceChargesPerTrainEntity> binder = new Binder<>(ServiceChargesPerTrainEntity.class);
        Editor<ServiceChargesPerTrainEntity> editor = this.getEditor();

        editor.setBinder(binder);

        IntegerField serviceCount = new IntegerField();

        serviceCountColumn.setEditorComponent(serviceCount);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        Grid.Column<ServiceChargesPerTrainEntity> editorColumn = this.addComponentColumn(serviceChargesPerTrainEntity -> {

            Button edit = new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(serviceChargesPerTrainEntity));
            edit.setEnabled(!editor.isOpen());

            editButtons.add(edit);

            return edit;
        });

        editor.addOpenListener(e -> editButtons.forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD),
                (ComponentEventListener<ClickEvent<Button>>) event -> {

                    editor.save();
                    this.getDataProvider().refreshAll();
                });

        save.addClassName("save");

        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL),
                new Icon(VaadinIcon.CLOSE_SMALL), e -> editor.cancel());
        cancel.addClassName("cancel");

        this.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        editorColumn.setEditorComponent(buttons);

        editor.addSaveListener(event -> {

            log.info("Saving changes for item: " + event.getItem().toString());

            serviceChargesPerTrainService.update(event.getItem());
        });

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE),
                new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<ServiceChargesPerTrainEntity> dataProvider =
                    (ListDataProvider<ServiceChargesPerTrainEntity>) this.getDataProvider();

            serviceChargesPerTrainService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));
    }
}