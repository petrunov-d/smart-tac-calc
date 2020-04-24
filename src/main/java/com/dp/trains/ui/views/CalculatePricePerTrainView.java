package com.dp.trains.ui.views;

import com.dp.trains.event.CPPTAllRowsRemovedEvent;
import com.dp.trains.event.CPPTFinalRowRemovedEvent;
import com.dp.trains.event.CPPTResetPageEvent;
import com.dp.trains.event.CPPTRowDoneEvent;
import com.dp.trains.model.dto.CalculateFinalTaxPerTrainDto;
import com.dp.trains.model.dto.LocomotiveSeriesDto;
import com.dp.trains.model.entities.CarrierCompanyEntity;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.model.entities.TrainTypeEntity;
import com.dp.trains.services.*;
import com.dp.trains.ui.components.cppt.CalculatePricePerTrainLayout;
import com.dp.trains.ui.components.dialogs.BasicInfoDialog;
import com.dp.trains.ui.components.dialogs.ConfirmLeaveCalculateTaxPerTrainPageDialog;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.dp.trains.utils.LocaleKeys.*;

@Slf4j
@UIScope
@SpringComponent
@Route(value = CalculatePricePerTrainView.NAV_CALCULATE_PRICE_PER_TRAIN, layout = MainLayout.class)
public class CalculatePricePerTrainView extends Composite<Div> implements BeforeLeaveObserver {

    static final String NAV_CALCULATE_PRICE_PER_TRAIN = "calculate_price_per_train";

    private H3 totalKilometersTitle;
    private H3 totalBruttoKilometersTitle;
    private H3 titleFinalTax;

    private Button add;
    private Button finalize;
    private Button calculateFinalTax;

    private final NumberField tonnage;
    private final NumberField trainLength;
    private final IntegerField trainNumber;
    private final TextArea calendar;
    private final TextArea note;

    private final Select<TrainTypeEntity> trainType;
    private final Select<StrategicCoefficientEntity> strategicCoefficientSelect;
    private final Select<CarrierCompanyEntity> carrierCompanySelect;
    private Select<LocomotiveSeriesDto> locomotiveSeries;

    private final CalculatePricePerTrainLayout calculatePricePerTrainLayout;

    @Autowired
    private TrainTypeService trainTypeService;
    @Autowired
    private SectionsService sectionsService;
    @Autowired
    private StrategicCoefficientService strategicCoefficientService;
    @Autowired
    private ServiceChargesPerTrainService serviceChargesPerTrainService;
    @Autowired
    private TaxForServicesPerTrainService taxForServicesPerTrainService;
    @Autowired
    private CarrierCompanyService carrierCompanyService;

    public CalculatePricePerTrainView() {

        calculatePricePerTrainLayout = new CalculatePricePerTrainLayout();

        HorizontalLayout baseParametersLayout = new HorizontalLayout();

        trainNumber = new IntegerField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_NUMBER));
        trainNumber.addValueChangeListener(event -> {

            calculatePricePerTrainLayout.updateTrainNumberForRows(event.getValue());
            shouldEnableAddButton();
        });
        trainNumber.setValueChangeMode(ValueChangeMode.EAGER);

        trainType = new Select<>();
        trainType.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_TYPE));
        trainType.addValueChangeListener(event -> shouldEnableAddButton());

        tonnage = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TONNAGE));
        tonnage.addValueChangeListener(event -> shouldEnableAddButton());
        tonnage.setValueChangeMode(ValueChangeMode.EAGER);

        trainLength = new NumberField();
        trainLength.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_LENGTH));

        note = new TextArea();
        note.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_NOTE));
        calendar = new TextArea();
        calendar.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CALENDAR));

        carrierCompanySelect = new Select<>();
        carrierCompanySelect.setLabel(getTranslation(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_SELECT_CARRIER_COMPANY)));
        carrierCompanySelect.addValueChangeListener(event -> {

            locomotiveSeries.setEnabled(true);
            locomotiveSeries.setItems(this.carrierCompanyService.getByCarrierName(event.getValue().getCarrierName()));
            locomotiveSeries.setItemLabelGenerator(x -> String.format("%s - %.3f", x.getSeries(), x.getWeight()));
        });

        locomotiveSeries = new Select<>();
        locomotiveSeries.setLabel(getTranslation(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_SELECT_LOCOMOTIVE_SERIES)));
        locomotiveSeries.setEnabled(false);

        baseParametersLayout.add(trainNumber, carrierCompanySelect, locomotiveSeries,
                trainType, tonnage, trainLength, calendar, note);

        strategicCoefficientSelect = new Select<>();
        strategicCoefficientSelect.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_STRATEGIC_COEFFICIENT));

        VerticalLayout footerContainer = new VerticalLayout(strategicCoefficientSelect,
                getKilometersSummaryLayout(), getFinalTaxLayout());

        footerContainer.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        calculatePricePerTrainLayout.add(getButtonOptionsBar());
        calculatePricePerTrainLayout.add(baseParametersLayout);

        getContent().add(calculatePricePerTrainLayout, footerContainer);
    }

    private void shouldEnableAddButton() {

        if (trainNumber.getValue() != null && trainType.getValue() != null && tonnage.getValue() != null) {

            add.setEnabled(true);
        }
    }

    @PostConstruct
    public void populateDropDowns() {

        trainType.setItems(trainTypeService.fetch(0, 0));
        trainType.setItemLabelGenerator(TrainTypeEntity::getName);

        carrierCompanySelect.setItems(this.carrierCompanyService.fetch(0, 0));
        carrierCompanySelect.setItemLabelGenerator(CarrierCompanyEntity::getCarrierName);

        strategicCoefficientSelect.setItems(strategicCoefficientService.fetch(0, 0));
        strategicCoefficientSelect.setItemLabelGenerator(StrategicCoefficientEntity::getName);

        EventBusHolder.getEventBus().register(this);
    }

    private HorizontalLayout getFinalTaxLayout() {

        HorizontalLayout finalTaxLayout = new HorizontalLayout();

        titleFinalTax = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX));

        finalTaxLayout.add(new VerticalLayout(titleFinalTax));

        return finalTaxLayout;
    }

    private HorizontalLayout getKilometersSummaryLayout() {

        totalKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS));
        totalBruttoKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS));

        HorizontalLayout kilometersSummaryFieldsLayout = new HorizontalLayout();

        kilometersSummaryFieldsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        kilometersSummaryFieldsLayout.setWidth("100%");

        kilometersSummaryFieldsLayout.add(new VerticalLayout(totalKilometersTitle), new VerticalLayout(totalBruttoKilometersTitle));

        return kilometersSummaryFieldsLayout;
    }

    private HorizontalLayout getButtonOptionsBar() {

        add = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD), VaadinIcon.PLUS.create());
        add.setEnabled(false);

        finalize = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD_FINAL_STATION), VaadinIcon.BAN.create());
        finalize.setEnabled(false);

        calculateFinalTax = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CALCULATE_FINAL_TAX), VaadinIcon.CALC_BOOK.create());
        calculateFinalTax.setEnabled(false);

        Button doNewCalculation = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_DO_NEW_CALCULATION), VaadinIcon.REFRESH.create());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(add, finalize, doNewCalculation, calculateFinalTax);

        add.addClickListener(e -> addRow(false));
        finalize.addClickListener(e -> addRow(true));

        calculateFinalTax.addClickListener(event -> calculateFinalTax());
        doNewCalculation.addClickListener(e -> resetPageState());

        return buttonLayout;
    }

    private void addRow(boolean isFinal) {

        calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                isFinal, sectionsService, serviceChargesPerTrainService, tonnage.getValue());

        add.setEnabled(false);
        finalize.setEnabled(false);
    }

    private void resetPageState() {

        calculatePricePerTrainLayout.resetContainer();
        strategicCoefficientSelect.setValue(null);
        trainType.setValue(null);
        trainNumber.setValue(null);
        tonnage.setValue(null);
        finalize.setEnabled(false);
        add.setEnabled(false);
        calculateFinalTax.setEnabled(false);
        totalKilometersTitle.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS));
        totalBruttoKilometersTitle.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS));
        titleFinalTax.setText(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX));
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
    }

    @Subscribe
    public void resetPageStateFromDialog(CPPTResetPageEvent cpptResetPageEvent) {

        this.resetPageState();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {

        event.postpone();

        ConfirmLeaveCalculateTaxPerTrainPageDialog confirmLeaveCalculateTaxPerTrainPageDialog =
                new ConfirmLeaveCalculateTaxPerTrainPageDialog(event);

        confirmLeaveCalculateTaxPerTrainPageDialog.open();
    }

    private void calculateFinalTax() {

        this.calculateFinalTax.setEnabled(false);

        CalculateFinalTaxPerTrainDto calculateFinalTaxPerTrainDto = this.taxForServicesPerTrainService
                .calculateFinalTaxForTrain(this.calculatePricePerTrainLayout.gatherAllRowData(),
                        strategicCoefficientSelect.getValue(), trainNumber.getValue(), trainType.getValue());

        if (calculateFinalTaxPerTrainDto.getStackTrace() != null) {

            Dialog dialog = new BasicInfoDialog(getTranslation(CALCULATE_TAX_PER_TRAIN_ERROR_IN_CALCULATION)
                    + calculateFinalTaxPerTrainDto.getStackTrace());

            dialog.open();

        } else {

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
}