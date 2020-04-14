package com.dp.trains.ui.components.dialogs;

import com.dp.trains.services.BaseImportService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import lombok.extern.slf4j.Slf4j;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
public class ConfirmImportDialog extends SmartTACCalcDialogBase {

    public ConfirmImportDialog(BaseImportService baseImportService, Select<String> select) {

        super();

        H3 h3 = new H3(getTranslation(CONFIRM_IMPORT_DIALOG_TITLE));

        Button ok = new Button(getTranslation(CONFIRM_IMPORT_DIALOG_DELETE_OLD_DATA), new Icon(VaadinIcon.DEL_A));
        ok.addClickListener(e -> {
            baseImportService.deleteAll();
            select.setValue("");
            this.close();
        });

        Button cancel = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));
        cancel.addClickListener(e -> {
            select.setValue("");
            this.close();
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(h3);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(ok, cancel);
        verticalLayout.add(horizontalLayout);

        this.add(verticalLayout);
    }
}