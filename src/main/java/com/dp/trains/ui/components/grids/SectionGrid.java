package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.SubSectionService;
import com.dp.trains.ui.components.dialogs.EditSubSectionsDialog;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@SuppressWarnings("unchecked")
public class SectionGrid extends SmartTACCalcGrid<SectionEntity> {

    public SectionGrid(SectionsService sectionsService, SubSectionService subSectionService,
                       LineNumberService lineNumberService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(sectionsService.fetch(0, 0)));

        Grid.Column<SectionEntity> lineNumberColumn = this.addColumn(SectionEntity::getLineNumber)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> firstKeyPointColumn = this.addColumn(SectionEntity::getFirstKeyPoint)
                .setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> lastKeyPointColumn = this.addColumn(SectionEntity::getLastKeyPoint)
                .setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> isElectrifiedColumn = this.addColumn(new ComponentRenderer<>(userEntity -> {

            Checkbox isElectrifiedCheckBox = new Checkbox();
            isElectrifiedCheckBox.setValue(userEntity.getIsElectrified() == null ? false : userEntity.getIsElectrified());
            isElectrifiedCheckBox.setEnabled(false);
            return isElectrifiedCheckBox;

        })).setHeader(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_IS_ELECTRIFED))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> kilometersBetweenStationsColumn = this.addColumn(SectionEntity::getKilometersBetweenStations)
                .setHeader(getTranslation(GRID_SECTION_COLUMN_HEADER_KILOMETERS_BETWEEN_STATIONS))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> lineTypeColumn = this.addColumn(SectionEntity::getLineType)
                .setHeader(getTranslation(GRID_LINE_TYPE_COLUMN_HEADER_LINE_TYPE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<SectionEntity> unitPriceColumn = this.addColumn(SectionEntity::getUnitPrice)
                .setHeader(getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE))
                .setSortable(true)
                .setResizable(true);

        Binder<SectionEntity> binder = new Binder<>(SectionEntity.class);
        Editor<SectionEntity> editor = this.getEditor();

        editor.setBinder(binder);

        ComboBox<Integer> lineNumber = new ComboBox<>();
        lineNumber.setItems(lineNumberService.getLineNumbersAsInts());

        TextField firstKeyPoint = new TextField();
        TextField lastKeyPoint = new TextField();
        Checkbox isElectrified = new Checkbox();
        NumberField kilometersBtweenStations = new NumberField();
        TextField lineType = new TextField();
        NumberField unitPrice = new NumberField();

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind("lineNumber");

        binder.forField(firstKeyPoint)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT_VALIDATION)))
                .withStatusLabel(new Label(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT_VALIDATION)))
                .bind("firstKeyPoint");

        binder.forField(lastKeyPoint)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT_VALIDATION)))
                .withStatusLabel(new Label(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT_VALIDATION)))
                .bind("lastKeyPoint");

        binder.forField(isElectrified)
                .asRequired()
                .withStatusLabel(new Label(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_IS_ELECTRIFED_VALIDATION)))
                .bind("isElectrified");

        binder.forField(unitPrice)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(GRID_SECTION_COLUMN_HEADER_KILOMETERS_BETWEEN_STATIONS_VALIDATION)))
                .withStatusLabel(new Label(getTranslation(GRID_SECTION_COLUMN_HEADER_KILOMETERS_BETWEEN_STATIONS_VALIDATION)))
                .bind("kilometersBetweenStations");

        binder.forField(lineType)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_LINE_TYPE_COLUMN_VALIDATION_LINE_TYPE)))
                .withStatusLabel(new Label(getTranslation(GRID_LINE_TYPE_COLUMN_VALIDATION_LINE_TYPE)))
                .bind("lineType");

        binder.forField(unitPrice)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_UNIT_PRICE)))
                .withStatusLabel(new Label(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_UNIT_PRICE)))
                .bind("unitPrice");

        lineNumberColumn.setEditorComponent(lineNumber);
        firstKeyPointColumn.setEditorComponent(firstKeyPoint);
        lastKeyPointColumn.setEditorComponent(lastKeyPoint);
        isElectrifiedColumn.setEditorComponent(isElectrified);
        kilometersBetweenStationsColumn.setEditorComponent(kilometersBtweenStations);
        lineTypeColumn.setEditorComponent(lineType);
        unitPriceColumn.setEditorComponent(unitPrice);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        Grid.Column<SectionEntity> editorColumn = this.addComponentColumn(sectionEntity -> {

            Button edit = new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(sectionEntity));
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

            sectionsService.update(event.getItem());
        });

        this.addComponentColumn(item -> new Button(getTranslation(GRID_SECTION_BUTTON_VIEW_SUB_SECTIONS_LABEL),
                new Icon(VaadinIcon.EYE), click -> {

            Dialog editUserDialog = new EditSubSectionsDialog(item, subSectionService);
            editUserDialog.open();
        }));

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH),
                click -> {

                    ListDataProvider<SectionEntity> dataProvider =
                            (ListDataProvider<SectionEntity>) this.getDataProvider();

                    sectionsService.remove(item);
                    dataProvider.getItems().remove(item);
                    dataProvider.refreshAll();
                }));
    }
}