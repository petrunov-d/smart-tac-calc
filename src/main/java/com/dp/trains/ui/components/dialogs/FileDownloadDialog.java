package com.dp.trains.ui.components.dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.dp.trains.utils.LocaleKeys.REPORTS_BUTTON_LABEL_GET_AS_EXCEL;
import static com.dp.trains.utils.LocaleKeys.SHARED_BUTTON_TEXT_CANCEL;

@Slf4j
public class FileDownloadDialog extends SmartTACCalcDialogBase {

    private final File file;

    public FileDownloadDialog(File file) {

        super();

        this.file = file;

        Button cancelDialog = new Button(getTranslation(SHARED_BUTTON_TEXT_CANCEL), new Icon(VaadinIcon.CLOSE_SMALL));
        cancelDialog.addClickListener(event -> this.close());

        VerticalLayout verticalLayout = new VerticalLayout();

        FileDownloadWrapper fileDownloadWrapper = new FileDownloadWrapper(new StreamResource(file.getName(), () -> {

            try {

                return new FileInputStream(this.file.getAbsolutePath());

            } catch (FileNotFoundException e) {

                log.error("Error in FileDownloadDialog Dialog: ", e);

                return new ByteArrayInputStream(new byte[]{});
            }
        }));

        fileDownloadWrapper.setText(getTranslation(REPORTS_BUTTON_LABEL_GET_AS_EXCEL));

        verticalLayout.setSizeFull();
        verticalLayout.add(fileDownloadWrapper);
        verticalLayout.add(cancelDialog);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.add(verticalLayout);
    }

    @Override
    public void close() {

        super.close();
        FileUtils.deleteQuietly(this.file);
    }
}