package com.dp.trains.ui.views;

import com.dp.trains.common.ServiceEnum;
import com.dp.trains.common.ServiceRegistry;
import com.dp.trains.model.dto.*;
import com.dp.trains.services.BaseImportService;
import com.dp.trains.ui.components.common.BaseSmartTacCalcView;
import com.dp.trains.ui.components.dialogs.ConfirmImportDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.DtoPoijiHolder;
import com.dp.trains.utils.PoijiOptionsFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.poiji.bind.Poiji;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = ImportDataView.NAV_LOAD_DATA_VIEW, layout = MainLayout.class)
public class ImportDataView extends BaseSmartTacCalcView {

    static final String NAV_LOAD_DATA_VIEW = "load_data_view";

    private static final String FILE_TYPE_MS_XLS = "application/vnd.ms-excel";
    private static final String FILE_TYPE_MS_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final BiMap<String, DtoPoijiHolder> importItems = new ImmutableBiMap.Builder<String, DtoPoijiHolder>()
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_RAIL_STATIONS), new DtoPoijiHolder(RailStationDto.class,
                    ServiceEnum.RAIL_STATION_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SECTIONS), new DtoPoijiHolder(RawExcelSectionDto.class,
                    ServiceEnum.SECTIONS_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_TYPES), new DtoPoijiHolder(LineTypeDto.class,
                    ServiceEnum.LINE_TYPE_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_TRAIN_TYPES), new DtoPoijiHolder(TrainTypeDto.class,
                    ServiceEnum.TRAIN_TYPE_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_SERVICES), new DtoPoijiHolder(ServiceDto.class,
                    ServiceEnum.SERVICE_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_STRATEGIC_COEFFICIENTS), new DtoPoijiHolder(StrategicCoefficientDto.class,
                    ServiceEnum.STRATEGIC_COEFFICIENTS_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_FINANCIAL_DATA), new DtoPoijiHolder(FinancialDataDto.class,
                    ServiceEnum.FINANCIAL_DATA_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_TRAFFIC_DATA), new DtoPoijiHolder(TrafficDataDto.class,
                    ServiceEnum.TRAFFIC_DATA_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_LINE_NUMBERS), new DtoPoijiHolder(LineNumberDto.class,
                    ServiceEnum.LINE_NUMBER_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_UNIT_PRICE), new DtoPoijiHolder(UnitPriceDto.class,
                    ServiceEnum.UNIT_PRICE_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_CARRIER_COMPANY), new DtoPoijiHolder(CarrierCompanyDto.class,
                    ServiceEnum.CARRIER_COMPANY_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .put(getTranslation(EDIT_DATA_VIEW_IMPORT_LABEL_MARKUP_COEFFICIENT), new DtoPoijiHolder(MarkupCoefficientDto.class,
                    ServiceEnum.MARKUP_COEFFICIENT_SERVICE, PoijiOptionsFactory.defaultOptions()))
            .build();

    private final Upload upload;
    private final Select<String> dataTypeSelect;
    private DtoPoijiHolder currentlySelectedPair;

    @Autowired
    private ServiceRegistry serviceRegistry;

    public ImportDataView() {

        super();

        VerticalLayout verticalLayout = new VerticalLayout();

        H1 headerText = new H1(getTranslation(SHARED_APP_TITLE));

        verticalLayout.add(headerText);

        this.upload = getUploadItem();
        this.upload.setVisible(false);
        this.dataTypeSelect = getSelectItem();

        verticalLayout.add(dataTypeSelect);
        verticalLayout.add(upload);
        verticalLayout.setSizeFull();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        add(verticalLayout, getFooter());
    }

    private Upload getUploadItem() {

        MemoryBuffer buffer = new MemoryBuffer();

        Upload upload = new Upload(buffer);

        upload.setMaxFiles(1);
        upload.setDropLabel(new Label(getTranslation(LOAD_DATA_VIEW_SELECT_LABEL)));
        upload.setAcceptedFileTypes(FILE_TYPE_MS_XLS, FILE_TYPE_MS_XLSX);

        upload.addStartedListener(event -> Notification.show(getTranslation(LOAD_DATA_VIEW_NOTIFICATION_FILE_UPLOAD_STARTED),
                5000, Notification.Position.MIDDLE));

        upload.addFailedListener(event -> Notification.show(getTranslation(LOAD_DATA_VIEW_NOTIFICATION_FILE_UPLOAD_FAILED)
                + event.getReason().getMessage(), 5000, Notification.Position.MIDDLE));

        SecurityContext securityContext = SecurityContextHolder.getContext();

        upload.addSucceededListener(event -> {

            onSuccess(buffer, event, securityContext);

            Notification.show(getTranslation(LOAD_DATA_VIEW_NOTIFICATION_FILE_UPLOAD_SUCCEEDED),
                    5000, Notification.Position.MIDDLE);

            dataTypeSelect.setValue("");
        });

        return upload;
    }

    private void onSuccess(MemoryBuffer buffer, SucceededEvent event, SecurityContext securityContext) {

        try {

            log.info("File upload succeeded, proceeding...");

            MemoryBuffer receiver = (MemoryBuffer) event.getUpload().getReceiver();

            String filename = receiver.getFileName();

            File tmpFile = File.createTempFile("excel_upload_" + Calendar.getInstance().getTimeInMillis(),
                    filename.substring(filename.lastIndexOf(".")));

            log.info("Created temp file: " + tmpFile.getAbsolutePath());

            ByteArrayOutputStream bos = (ByteArrayOutputStream) receiver.getFileData().getOutputBuffer();

            FileUtils.writeByteArrayToFile(tmpFile, bos.toByteArray());

            SecurityContextHolder.getContext().setAuthentication(securityContext.getAuthentication());

            List<? extends ExcelImportDto> dtos = Poiji.fromExcel(tmpFile, currentlySelectedPair.getDtoClass(),
                    currentlySelectedPair.getPoijiOptions());

            serviceRegistry.getService(currentlySelectedPair.getServiceEnum()).importFromExcel(dtos);

            FileUtils.deleteQuietly(tmpFile);

            UI.getCurrent().getPage().reload();

        } catch (IOException e) {

            log.error("Exception processing file : ", e);

            Notification.show(getTranslation(LOAD_DATA_VIEW_NOTIFICATION_FILE_IMPORT_EXCEPTION),
                    5000, Notification.Position.MIDDLE);
        }
    }

    private Select<String> getSelectItem() {

        Select<String> dataTypeSelect = new Select<>();
        dataTypeSelect.setLabel(getTranslation(SELECT_FILE_TYPE_IMPORT_TITLE));
        dataTypeSelect.setItems(Lists.newArrayList(importItems.keySet()));

        dataTypeSelect.addValueChangeListener(event -> {

            if (!event.getValue().equals("")) {

                upload.setVisible(true);
                currentlySelectedPair = importItems.get(event.getValue());

                BaseImportService baseImportService = serviceRegistry.getService(currentlySelectedPair.getServiceEnum());

                if (baseImportService.count() > 0) {

                    Dialog confirmDialog = new ConfirmImportDialog(baseImportService, dataTypeSelect);
                    confirmDialog.open();
                }

                log.info("Currently selected pair: " + currentlySelectedPair.toString());
            } else {

                upload.setVisible(false);
                upload.getElement().setPropertyJson("files", Json.createArray());
            }
        });

        return dataTypeSelect;
    }
}