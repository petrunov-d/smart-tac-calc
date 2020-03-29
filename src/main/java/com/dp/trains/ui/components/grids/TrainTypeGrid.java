package com.dp.trains.ui.components.grids;

import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.services.TrainTypeService;
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
import com.vaadin.flow.component.textfield.IntegerField;
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
public class TrainTypeGrid extends SmartTACCalcGrid<TrainTypeEntity> {

    public TrainTypeGrid(TrainTypeService trainTypeService) {

        super();

        this.setDataProvider(DataProvider.ofCollection(trainTypeService.fetch(0, 0)));

        Grid.Column<TrainTypeEntity> codeColumn = this.addColumn(TrainTypeEntity::getCode)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_CODE))
                .setSortable(true)
                .setResizable(true);

        Grid.Column<TrainTypeEntity> nameColumn = this.addColumn(TrainTypeEntity::getName)
                .setHeader(getTranslation(GRID_TRAIN_TYPE_COLUMN_HEADER_NAME))
                .setSortable(true)
                .setResizable(true);

        Binder<TrainTypeEntity> binder = new Binder<>(TrainTypeEntity.class);
        Editor<TrainTypeEntity> editor = this.getEditor();

        editor.setBinder(binder);

        IntegerField code = new IntegerField();
        TextField name = new TextField();

        binder.forField(code)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_CODE_MESSAGE)))
                .withStatusLabel(new Label(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_CODE_MESSAGE)))
                .bind("code");

        binder.forField(name)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .withStatusLabel(new Label(getTranslation(GRID_TRAIN_TYPE_COLUMN_VALIDATION_NAME_MESSAGE)))
                .bind("name");

        codeColumn.setEditorComponent(code);
        nameColumn.setEditorComponent(name);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

        Grid.Column<TrainTypeEntity> editorColumn = this.addComponentColumn(trainTypeEntity -> {

            Button edit = new Button(getTranslation(SHARED_BUTTON_TEXT_EDIT), new Icon(VaadinIcon.EDIT));
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(trainTypeEntity));
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

            trainTypeService.update(event.getItem());
        });

        this.addComponentColumn(item -> new Button(getTranslation(SHARED_BUTTON_TEXT_DELETE), new Icon(VaadinIcon.TRASH), click -> {

            ListDataProvider<TrainTypeEntity> dataProvider = (ListDataProvider<TrainTypeEntity>) this.getDataProvider();

            trainTypeService.remove(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        }));
    }
}