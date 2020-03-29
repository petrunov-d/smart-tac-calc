package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.ServiceEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.ServiceService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class ServiceGrid extends SmartTACCalcGrid<ServiceEntity> {

    public ServiceGrid(ServiceService serviceService, LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(serviceService.fetch(0, 0)));

        Grid.Column<ServiceEntity> nameColumn = this.addColumn(ServiceEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> lineNumberColumn = this.addColumn(ServiceEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> metricColumn = this.addColumn(ServiceEntity::getMetric)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_METRIC))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> typeColumn = this.addColumn(ServiceEntity::getType)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<ServiceEntity> unitPriceColumn = this.addColumn(ServiceEntity::getUnitPrice)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        Binder<ServiceEntity> binder = new Binder<>(ServiceEntity.class);
        Editor<ServiceEntity> editor = this.getEditor();

        editor.setBinder(binder);

        TextField name = new TextField();
        ComboBox<Integer> lineNumber = new ComboBox<>();
        lineNumber.setItems(lineNumberService.getLineNumbersAsInts());
        TextField metric = new TextField();
        TextField type = new TextField();
        NumberField unitPrice = new NumberField();

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .withStatusLabel(new Label(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .bind("name");

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind("lineNumber");

        binder.forField(metric)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_METRIC)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_METRIC)))
                .bind("metric");

        binder.forField(type)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_TYPE)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_TYPE)))
                .bind("type");

        binder.forField(unitPrice)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_UNIT_PRICE)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_UNIT_PRICE)))
                .bind("unitPrice");

        nameColumn.setEditorComponent(name);
        lineNumberColumn.setEditorComponent(lineNumber);
        metricColumn.setEditorComponent(metric);
        typeColumn.setEditorComponent(type);
        unitPriceColumn.setEditorComponent(unitPrice);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        Grid.Column<ServiceEntity> editorColumn = this.addComponentColumn(serviceEntity -> {

            Button edit = new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(serviceEntity));
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

        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL), e -> editor.cancel());
        cancel.addClassName("cancel");

        this.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        editorColumn.setEditorComponent(buttons);

        editor.addSaveListener(event -> {

            log.info("Saving changes for item: " + event.getItem().toString());

            serviceService.update(event.getItem());
        });

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<ServiceEntity> dataProvider = (ListDataProvider<ServiceEntity>) this.getDataProvider();

            serviceService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));
    }
}
