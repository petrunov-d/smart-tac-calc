package com.dp.trains.ui.components.dialogs;

import com.dp.trains.ui.components.common.EmbeddedPdfDocument;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.dp.trains.utils.LocaleKeys.SHARED_BUTTON_TEXT_CANCEL;

@Slf4j
public class PdfViewerDialog extends SmartTACCalcDialogBase {

    private final File pdfFile;

    public PdfViewerDialog(File file) {

        super();

        this.pdfFile = file;

        setWidth("calc(90vw - (4*var(--lumo-space-m)))");
        setHeight("calc(50vw - (1*var(--lumo-space-m)))");

        Button cancelDialog = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));
        cancelDialog.addClickListener(event -> this.close());

        VerticalLayout verticalLayout = new VerticalLayout();

        EmbeddedPdfDocument embeddedPdfDocument = new EmbeddedPdfDocument(new StreamResource(pdfFile.getName(), () -> {

            try {

                return new FileInputStream(this.pdfFile.getAbsolutePath());

            } catch (FileNotFoundException e) {

                log.error("Error in PDF Viewer Dialog: ", e);

                return new ByteArrayInputStream(new byte[]{});
            }
        }));

        verticalLayout.setSizeFull();
        verticalLayout.add(cancelDialog);
        verticalLayout.add(embeddedPdfDocument);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }

    @Override
    public void close() {

        FileUtils.deleteQuietly(this.pdfFile);
        super.close();
    }
}