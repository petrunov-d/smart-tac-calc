package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.dto.SectionsDto;
import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.services.LineNumberService;
import com.dp.trains.services.LineTypeService;
import com.dp.trains.services.SectionsService;
import com.dp.trains.ui.validators.ValidatorFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
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
public class EditSectionDialog extends AddDialogBase {

    public EditSectionDialog(Grid currentlyActiveGrid, SectionsService sectionsService, LineTypeService lineTypeService,
                             LineNumberService lineNumberService, SectionEntity sectionEntity) {

        super(currentlyActiveGrid);
        FormLayout layoutWithBinder = new FormLayout();
        Binder<SectionsDto> binder = new Binder<>();

        SectionsDto sectionsDto = new SectionsDto();

        Select<Integer> lineNumber = new Select<>();

        lineNumber.setItems(lineNumberService.getLineNumbersAsInts());
        lineNumber.addValueChangeListener(event -> binder.validate());
        lineNumber.setRequiredIndicatorVisible(true);
        lineNumber.setValue(sectionEntity.getLineNumber());

        Select<String> lineType = new Select<>();
        lineType.setItems(lineTypeService.getLineTypes());
        lineType.addValueChangeListener(event -> binder.validate());
        lineType.setRequiredIndicatorVisible(true);
        lineType.setValue(sectionEntity.getLineType());

        TextArea firstKeyPoint = new TextArea();

        firstKeyPoint.setValueChangeMode(ValueChangeMode.EAGER);
        firstKeyPoint.addValueChangeListener(event -> binder.validate());
        firstKeyPoint.setRequiredIndicatorVisible(true);
        firstKeyPoint.setValue(sectionEntity.getFirstKeyPoint());

        TextArea lastKeyPoint = new TextArea();

        lastKeyPoint.setValueChangeMode(ValueChangeMode.EAGER);
        lastKeyPoint.addValueChangeListener(event -> binder.validate());
        lastKeyPoint.setRequiredIndicatorVisible(true);
        lastKeyPoint.setValue(sectionEntity.getLastKeyPoint());

        NumberField kilometersBetweenStations = new NumberField();

        kilometersBetweenStations.setValueChangeMode(ValueChangeMode.EAGER);
        kilometersBetweenStations.addValueChangeListener(event -> binder.validate());
        kilometersBetweenStations.setRequiredIndicatorVisible(true);
        kilometersBetweenStations.setValue(sectionEntity.getKilometersBetweenStations());

        Checkbox isElectrified = new Checkbox();
        isElectrified.setValue(sectionEntity.getIsElectrified());

        NumberField unitPrice = new NumberField();

        unitPrice.setValueChangeMode(ValueChangeMode.EAGER);
        unitPrice.addValueChangeListener(event -> binder.validate());
        unitPrice.setRequiredIndicatorVisible(true);
        unitPrice.setValue(sectionEntity.getUnitPrice());

        binder.forField(lineNumber)
                .asRequired()
                .withValidator(ValidatorFactory.defaultIntRangeValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(SectionsDto::getLineNumber, SectionsDto::setLineNumber);

        binder.forField(lineType)
                .asRequired()
                .withValidator(ValidatorFactory.requiredVarcharStringValidator(getTranslation(GRID_SERVICE_COLUMN_VALIDATION_LINE_NUMBER)))
                .bind(SectionsDto::getLineType, SectionsDto::setLineType);

        binder.forField(firstKeyPoint)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT_VALIDATION)))
                .bind(SectionsDto::getFirstKeyPoint, SectionsDto::setFirstKeyPoint);

        binder.forField(lastKeyPoint)
                .asRequired()
                .withValidator(ValidatorFactory.requiredStringValidator(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT_VALIDATION)))
                .bind(SectionsDto::getLastKeyPoint, SectionsDto::setLastKeyPoint);

        binder.forField(unitPrice)
                .asRequired()
                .withValidator(ValidatorFactory.defaultDoubleRangeValidator(getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_IS_ELECTRIFED_VALIDATION)))
                .bind(SectionsDto::getUnitPrice, SectionsDto::setUnitPrice);

        layoutWithBinder.addFormItem(lineNumber, getTranslation(GRID_SERVICE_COLUMN_HEADER_LINE_NUMBER));
        layoutWithBinder.addFormItem(lineType, getTranslation(GRID_LINE_TYPE_COLUMN_HEADER_LINE_TYPE));
        layoutWithBinder.addFormItem(firstKeyPoint, getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_FIRST_KEY_POINT));
        layoutWithBinder.addFormItem(lastKeyPoint, getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_LAST_KEY_POINT));
        layoutWithBinder.addFormItem(unitPrice, getTranslation(GRID_SERVICE_COLUMN_HEADER_UNIT_PRICE));
        layoutWithBinder.addFormItem(isElectrified, getTranslation(DIALOG_ADD_SECTION_FORM_ITEM_IS_ELECTRIFED));

        Button save = new Button(getTranslation(SHARED_BUTTON_TEXT_SAVE), new Icon(VaadinIcon.UPLOAD));
        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(sectionsDto)) {

                ListDataProvider<SectionEntity> dataProvider = (ListDataProvider<SectionEntity>) currentlyActiveGrid.getDataProvider();

                sectionsDto.setIsElectrified(isElectrified.getValue());

                SectionEntity sectionEntityUpdate = sectionsService.update(sectionsDto, sectionEntity.getId());
                dataProvider.getItems().remove(sectionEntity);
                dataProvider.getItems().add(sectionEntityUpdate);
                dataProvider.refreshAll();
                this.close();

            } else {

                BinderValidationStatus<SectionsDto> validate = binder.validate();

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
            lineNumber.setValue(null);
            lineType.setValue("");
            unitPrice.setValue(null);
            firstKeyPoint.setValue("");
            lastKeyPoint.setValue("");
            isElectrified.setValue(false);
            this.close();
        });

        VerticalLayout verticalLayout = getDefaultDialogLayout(getTranslation("Editing Section"), layoutWithBinder, actions);

        this.add(verticalLayout);
    }
}
