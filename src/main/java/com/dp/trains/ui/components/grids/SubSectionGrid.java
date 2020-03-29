package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.model.entities.SubSectionEntity;
import com.dp.trains.services.SubSectionService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
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
public class SubSectionGrid extends SmartTACCalcGrid<SubSectionEntity> {

    public SubSectionGrid(SectionEntity sectionEntity, SubSectionService subSectionService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(sectionEntity.getSubSectionEntities()));

        Grid.Column<SubSectionEntity> kilometersColumn = this.addColumn(SubSectionEntity::getKilometers)
                .setHeader(getTranslation(GRID_SUB_SECTION_COLUMN_HEADER_KILOMETERS))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SubSectionEntity> nonKeyStationColumn = this
                .addColumn(SubSectionEntity::getNonKeyStation)
                .setHeader(getTranslation(GRID_SUB_SECTION_COLUMN_HEADER_NON_KEY_STATION))
                .setSortable(true)
                .setResizable(true);

        Binder<SubSectionEntity> binder = new Binder<>(SubSectionEntity.class);
        Editor<SubSectionEntity> editor = this.getEditor();

        editor.setBinder(binder);

        NumberField kilometers = new NumberField();
        TextField nonKeyStation = new TextField();

        binder.forField(kilometers)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(GRID_SUB_SECTION_COLUMN_VALIDATION_KILOMETERS_MESSAGE)))
                .withStatusLabel(new Label(getTranslation(GRID_SUB_SECTION_COLUMN_VALIDATION_KILOMETERS_MESSAGE)))
                .bind("kilometers");

        binder.forField(nonKeyStation)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_SUB_SECTION_COLUMN_VALIDATION_NON_KEY_STATION_MESSAGE)))
                .withStatusLabel(new Label(getTranslation(GRID_SUB_SECTION_COLUMN_VALIDATION_NON_KEY_STATION_MESSAGE)))
                .bind("nonKeyStation");

        kilometersColumn.setEditorComponent(kilometers);
        nonKeyStationColumn.setEditorComponent(nonKeyStation);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        Grid.Column<SubSectionEntity> editorColumn = this.addComponentColumn(subSectionEntity -> {

            Button edit = new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(subSectionEntity));
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

            subSectionService.update(event.getItem());
        });

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<SubSectionEntity> dataProvider = (ListDataProvider<SubSectionEntity>) this
                    .getDataProvider();

            subSectionService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));
    }
}
