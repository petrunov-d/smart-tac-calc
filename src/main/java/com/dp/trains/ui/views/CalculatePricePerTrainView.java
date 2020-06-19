package com.dp.trains.ui.views;

import com.dp.trains.event.CPPTAllRowsRemovedEvent;
import com.dp.trains.event.CPPTFinalRowRemovedEvent;
import com.dp.trains.event.CPPTResetPageEvent;
import com.dp.trains.event.CPPTRowDoneEvent;
import com.dp.trains.model.dto.CalculateFinalTaxPerTrainDto;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.model.entities.user.UserAccess;
import com.dp.trains.services.*;
import com.dp.trains.ui.components.common.UserPermissionAwareView;
import com.dp.trains.ui.components.cppt.CalculatePricePerTrainLayout;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.ConfirmDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = CalculatePricePerTrainView.NAV_CALCULATE_PRICE_PER_TRAIN, layout = MainLayout.class)
public class CalculatePricePerTrainView extends UserPermissionAwareView implements BeforeLeaveObserver {

    static final String NAV_CALCULATE_PRICE_PER_TRAIN = "calculate_price_per_train";

    private H3 totalKilometersTitle;
    private H3 totalBruttoKilometersTitle;
    private H3 titleFinalTax;

    private Button add;
    private Button finalize;
    private Button calculateFinalTax;

    private NumberField tonnage;
    private NumberField trainLength;
    private IntegerField trainNumber;

    private TextArea calendar;
    private TextArea note;

    private Select<TrainTypeEntity> trainType;
    private Select<StrategicCoefficientEntity> strategicCoefficientSelect;
    private Select<String> carrierCompanySelect;
    private Select<LocomotiveSeriesDto> locomotiveSeriesDtoSelect;

    private final CalculatePricePerTrainLayout calculatePricePerTrainLayout;

    private final TrainTypeService trainTypeService;
    private final SectionsService sectionsService;
    private final StrategicCoefficientService strategicCoefficientService;
    private final ServiceChargesPerTrainService serviceChargesPerTrainService;
    private final TaxPerTrainService taxPerTrainService;
    private final CarrierCompanyService carrierCompanyService;
    private final RailStationService railStationService;

    public CalculatePricePerTrainView(@Autowired SectionsService sectionsService,
                                      @Autowired TrainTypeService trainTypeService,
                                      @Autowired StrategicCoefficientService strategicCoefficientService,
                                      @Autowired ServiceChargesPerTrainService serviceChargesPerTrainService,
                                      @Autowired TaxPerTrainService taxPerTrainService,
                                      @Autowired CarrierCompanyService carrierCompanyService,
                                      @Autowired RailStationService railStationService) {

        super();

        this.calculatePricePerTrainLayout = new CalculatePricePerTrainLayout();

        HorizontalLayout baseParametersLayout = new HorizontalLayout();

        initializeTrainNumber();
        initializeTrainType();
        initializeTonnage();
        initializeTrainLength();
        initializeNote();
        initializeCalendar();
        initializeCarrierCompanySelect();
        initializeLocomotiveSeriesDtoSelect();

        baseParametersLayout.add(trainNumber, carrierCompanySelect, locomotiveSeriesDtoSelect,
                trainType, tonnage, trainLength, calendar, note);

        initializeStrategicCoefficientsSelect();

        VerticalLayout footerContainer = new VerticalLayout(strategicCoefficientSelect,
                getKilometersSummaryLayout(), getFinalTaxLayout());

        footerContainer.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        calculatePricePerTrainLayout.add(getButtonOptionsBar());
        calculatePricePerTrainLayout.add(baseParametersLayout);

        this.add(calculatePricePerTrainLayout, footerContainer);

        this.sectionsService = sectionsService;
        this.trainTypeService = trainTypeService;
        this.strategicCoefficientService = strategicCoefficientService;
        this.serviceChargesPerTrainService = serviceChargesPerTrainService;
        this.taxPerTrainService = taxPerTrainService;
        this.carrierCompanyService = carrierCompanyService;
        this.railStationService = railStationService;
    }

    private void initializeStrategicCoefficientsSelect() {

        this.strategicCoefficientSelect = new Select<>();
        this.strategicCoefficientSelect.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_STRATEGIC_COEFFICIENT));
    }

    private void initializeLocomotiveSeriesDtoSelect() {

        this.locomotiveSeriesDtoSelect = new Select<>();
        this.locomotiveSeriesDtoSelect.setLabel(getTranslation(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_SELECT_LOCOMOTIVE_SERIES)));
        this.locomotiveSeriesDtoSelect.setEnabled(false);
    }

    private void initializeCalendar() {

        this.calendar = new TextArea();
        this.calendar.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CALENDAR));
    }

    private void initializeNote() {

        this.note = new TextArea();
        this.note.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_NOTE));
    }

    private void initializeTrainLength() {

        this.trainLength = new NumberField();
        this.trainLength.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_LENGTH));
        this.trainLength.setValueChangeMode(ValueChangeMode.EAGER);
        this.trainLength.addValueChangeListener(event -> shouldEnableAddButton());
    }

    private void initializeCarrierCompanySelect() {

        this.carrierCompanySelect = new Select<>();
        this.carrierCompanySelect.setLabel(getTranslation(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_SELECT_CARRIER_COMPANY)));
        this.carrierCompanySelect.addValueChangeListener(event -> {

            if (event.getValue() != null) {

                Collection<LocomotiveSeriesDto> locomotiveSeriesDtoList = this.carrierCompanyService.getByCarrierName(event.getValue());

                this.locomotiveSeriesDtoSelect.setEnabled(true);
                this.locomotiveSeriesDtoSelect.setItems(locomotiveSeriesDtoList);
                this.calculatePricePerTrainLayout.setLocomotiveSeriesDtos(locomotiveSeriesDtoList);
                this.locomotiveSeriesDtoSelect.setItemLabelGenerator(x -> String.format("%s - %.3f", x.getSeries(), x.getWeight()));

            } else {

                this.carrierCompanySelect.setItems(this.carrierCompanyService.fetchNames());
                this.carrierCompanySelect.setEnabled(true);
            }
        });
    }

    private void initializeTonnage() {

        tonnage = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TONNAGE));
        tonnage.addValueChangeListener(event -> shouldEnableAddButton());
        tonnage.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private void initializeTrainType() {

        this.trainType = new Select<>();
        this.trainType.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_TYPE));
        this.trainType.addValueChangeListener(event -> shouldEnableAddButton());
    }

    private void initializeTrainNumber() {

        this.trainNumber = new IntegerField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_NUMBER));
        this.trainNumber.setValueChangeMode(ValueChangeMode.EAGER);
        this.trainNumber.addValueChangeListener(event -> {

            calculatePricePerTrainLayout.updateTrainNumberForRows(event.getValue());
            shouldEnableAddButton();
        });
    }

    private void shouldEnableAddButton() {

        if (trainNumber.getValue() != null && trainType.getValue() != null && tonnage.getValue() != null
                && carrierCompanySelect.getValue() != null
                && locomotiveSeriesDtoSelect.getValue() != null && this.trainLength.getValue() != null) {

            add.setEnabled(true);
        }
    }

    @PostConstruct
    public void populateDropDowns() {

        this.trainType.setItems(trainTypeService.fetch(0, 0));
        this.trainType.setItemLabelGenerator(TrainTypeEntity::getName);

        this.carrierCompanySelect.setItems(this.carrierCompanyService.fetchNames());

        this.strategicCoefficientSelect.setItems(strategicCoefficientService.fetch(0, 0));
        this.strategicCoefficientSelect.setItemLabelGenerator(StrategicCoefficientEntity::getName);

        EventBusHolder.getEventBus().register(this);
    }

    private HorizontalLayout getFinalTaxLayout() {

        HorizontalLayout finalTaxLayout = new HorizontalLayout();

        this.titleFinalTax = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX));
        this.titleFinalTax.setVisible(false);

        finalTaxLayout.add(new VerticalLayout(titleFinalTax));

        return finalTaxLayout;
    }

    private HorizontalLayout getKilometersSummaryLayout() {

        this.totalKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS));
        this.totalBruttoKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS));

        this.totalKilometersTitle.setVisible(false);
        this.totalBruttoKilometersTitle.setVisible(false);

        HorizontalLayout kilometersSummaryFieldsLayout = new HorizontalLayout();

        kilometersSummaryFieldsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        kilometersSummaryFieldsLayout.setWidth("100%");

        kilometersSummaryFieldsLayout.add(new VerticalLayout(totalKilometersTitle), new VerticalLayout(totalBruttoKilometersTitle));

        return kilometersSummaryFieldsLayout;
    }

    private HorizontalLayout getButtonOptionsBar() {

        this.add = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD), VaadinIcon.PLUS.create());
        this.add.setEnabled(false);

        this.finalize = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD_FINAL_STATION), VaadinIcon.BAN.create());
        this.finalize.setEnabled(false);

        this.calculateFinalTax = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CALCULATE_FINAL_TAX), VaadinIcon.CALC_BOOK.create());
        this.calculateFinalTax.setEnabled(false);

        Button doNewCalculation = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_DO_NEW_CALCULATION), VaadinIcon.REFRESH.create());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(add, finalize, doNewCalculation, calculateFinalTax);

        this.add.addClickListener(e -> addRow(false));
        this.finalize.addClickListener(e -> addRow(true));

        this.calculateFinalTax.addClickListener(event -> calculateFinalTax());
        doNewCalculation.addClickListener(e -> resetPageState());

        return buttonLayout;
    }

    private void addRow(boolean isFinal) {

        this.calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                isFinal,
                this.railStationService,
                this.serviceChargesPerTrainService,
                this.carrierCompanyService,
                this.tonnage.getValue(),
                this.carrierCompanyService.getByName(this.carrierCompanySelect.getValue()),
                this.locomotiveSeriesDtoSelect.getDataProvider().fetch(new Query<>()).collect(Collectors.toCollection(ArrayList::new)),
                this.locomotiveSeriesDtoSelect.getValue(),
                this.trainLength.getValue());

        this.add.setEnabled(false);
        this.finalize.setEnabled(false);
        toggleMainFields(false);
    }

    private void resetPageState() {

        this.calculatePricePerTrainLayout.resetContainer();
        this.strategicCoefficientSelect.setValue(null);
        this.trainType.setValue(null);
        this.trainNumber.setValue(null);
        this.tonnage.setValue(null);
        this.finalize.setEnabled(false);
        this.add.setEnabled(false);
        this.calendar.setValue("");
        this.note.setValue("");
        this.trainLength.setValue(null);
        this.locomotiveSeriesDtoSelect.setValue(null);
        this.carrierCompanySelect.setValue(null);
        this.calculateFinalTax.setEnabled(false);
        this.totalKilometersTitle.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS));
        this.totalBruttoKilometersTitle.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS));
        this.titleFinalTax.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX));
        this.titleFinalTax.setVisible(false);
        this.totalKilometersTitle.setVisible(false);
        this.totalBruttoKilometersTitle.setVisible(false);

        toggleMainFields(true);
    }

    private void toggleMainFields(boolean b) {

        this.tonnage.setEnabled(b);
        this.trainLength.setEnabled(b);
        this.trainNumber.setEnabled(b);
        this.calendar.setEnabled(b);
        this.note.setEnabled(b);
        this.trainType.setEnabled(b);
        this.carrierCompanySelect.setEnabled(b);
        this.locomotiveSeriesDtoSelect.setEnabled(b);
    }

    @Subscribe
    public void handleRowDoneEvent(CPPTRowDoneEvent cpptRowDoneEvent) {

        if (!cpptRowDoneEvent.getIsFinal()) {

            this.add.setEnabled(true);
            this.finalize.setEnabled(true);
            this.calculateFinalTax.setEnabled(false);
        } else {

            this.add.setEnabled(false);
            this.finalize.setEnabled(false);
            this.calculateFinalTax.setEnabled(true);
        }
    }

    @Subscribe
    public void handleFinalRowRemovedEvent(CPPTFinalRowRemovedEvent cpptFinalRowRemovedEvent) {

        this.add.setEnabled(true);
        this.finalize.setEnabled(true);
    }

    @Subscribe
    public void handleAllRowsRemovedEvent(CPPTAllRowsRemovedEvent cpptAllRowsRemovedEvent) {

        this.add.setEnabled(true);
        this.finalize.setEnabled(false);
        this.calculateFinalTax.setEnabled(false);
        toggleMainFields(true);
    }

    @Subscribe
    public void resetPageStateFromDialog(CPPTResetPageEvent cpptResetPageEvent) {

        this.resetPageState();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {

        beforeLeaveEvent.postpone();

        ConfirmDialog confirmDialog = new ConfirmDialog
                .Builder()
                .withTitle(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CONFIRM_LEAVE_MESSAGE))
                .withOkButtonListener(clickEvent -> {

                    EventBusHolder.getEventBus().post(new CPPTResetPageEvent());
                    beforeLeaveEvent.getContinueNavigationAction().proceed();
                })
                .build();

        confirmDialog.open();
    }

    private void calculateFinalTax() {

        Boolean hasPreviousRecords = this.taxPerTrainService.hasRecordsForTrainNumber(this.trainNumber.getValue());

        if (hasPreviousRecords) {

            ConfirmDialog confirmDialog = new ConfirmDialog
                    .Builder()
                    .withTitle(getTranslation(CONFIRM_DELETE_OLD_TAC))
                    .withOkButtonListener(event -> taxPerTrainService.deleteByTrainNumber(trainNumber.getValue()))
                    .build();

            confirmDialog.open();
        }

        this.calculateFinalTax.setEnabled(false);

        List<CalculateTaxPerTrainRowDataDto> calculateTaxPerTrainRowDataDtos =
                this.sectionsService.findByRawDtos(this.calculatePricePerTrainLayout.gatherAllRowData());

        CalculateFinalTaxPerTrainDto calculateFinalTaxPerTrainDto =
                this.taxPerTrainService.calculateFinalTaxForTrain(calculateTaxPerTrainRowDataDtos,
                        strategicCoefficientSelect.getValue(),
                        trainNumber.getValue(),
                        calendar.getValue(),
                        note.getValue(),
                        trainLength.getValue(),
                        trainType.getValue());

        if (calculateFinalTaxPerTrainDto.getStackTrace() != null) {

            Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_TAX_PER_TRAIN_ERROR_IN_CALCULATION)
                    + calculateFinalTaxPerTrainDto.getStackTrace());

            dialog.open();

        } else {

            this.titleFinalTax.setVisible(true);
            this.totalKilometersTitle.setVisible(true);
            this.totalBruttoKilometersTitle.setVisible(true);

            this.totalKilometersTitle.setText(String.format("%s %.3f",
                    getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS),
                    calculateFinalTaxPerTrainDto.getTotalKilometers()));

            this.totalBruttoKilometersTitle.setText(String.format("%s %.3f",
                    getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS),
                    calculateFinalTaxPerTrainDto.getTotalBruttoTonneKilometers()));

            this.titleFinalTax.setText(String.format("%s %.5f",
                    getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX),
                    calculateFinalTaxPerTrainDto.getFinalTax()));
        }
    }

    @Override
    public UserAccess getViewUserAccessPermission() {

        return UserAccess.CALCULATE_SINGLE_PRICE;
    }
}