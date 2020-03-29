package com.dp.trains.ui.components.dialogs;

import com.dp.trains.model.entities.SectionEntity;
import com.dp.trains.services.SubSectionService;
import com.dp.trains.ui.components.grids.SubSectionGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class EditSubSectionsDialog extends Dialog {

    public EditSubSectionsDialog(SectionEntity sectionEntity, SubSectionService subSectionService) {

        this.setCloseOnEsc(false);
        this.setCloseOnOutsideClick(false);

        setWidth("calc(90vw - (4*var(--lumo-space-m)))");
        setHeight("calc(50vw - (1*var(--lumo-space-m)))");

        H3 h3Heading = new H3(getTranslation(DIALOG_EDITING_SECTION_TITLE));

        Button cancelDialog = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        cancelDialog.addClickListener(event -> this.close());

        Grid subSectionGrid = new SubSectionGrid(sectionEntity, subSectionService);

        Button button = new Button(new Icon(VaadinIcon.PLUS));
        button.setText(getTranslation(EDIT_DATA_VIEW_BUTTON_TEXT_ADD_ITEM));
        button.addClickListener(event -> {

            Dialog dialog = new AddSubSectionDialog(subSectionGrid, subSectionService, sectionEntity);
            dialog.open();
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(button);
        verticalLayout.setSizeFull();
        verticalLayout.add(h3Heading);
        verticalLayout.add(subSectionGrid);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
        verticalLayout.add(cancelDialog);
    }
}