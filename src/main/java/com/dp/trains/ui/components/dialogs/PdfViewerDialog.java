package com.dp.trains.ui.components.dialogs;

import com.dp.trains.ui.components.common.EmbeddedPdfDocument;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URISyntaxException;

import static com.dp.trains.utils.LocaleKeys.SHARED_BUTTON_TEXT_CANCEL;

@Slf4j
public class PdfViewerDialog extends SmartTACCalcDialogBase {

    public PdfViewerDialog() throws URISyntaxException {

        super();

        setWidth("calc(90vw - (4*var(--lumo-space-m)))");
        setHeight("calc(50vw - (1*var(--lumo-space-m)))");

        H3 h3Heading = new H3(getTranslation("Viewing Report"));

        File f = new File(getClass().getResource("/report.pdf").toURI());

        Button cancelDialog = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));

        cancelDialog.addClickListener(event -> this.close());

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(h3Heading);
        add(new EmbeddedPdfDocument(f.getAbsolutePath()));


        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);

        verticalLayout.add(cancelDialog);
    }
}
