package com.dp.trains.ui.views;

import com.dp.trains.event.CPPTFinalRowRemovedEvent;
import com.dp.trains.event.CPPTRowDoneEvent;
import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.services.StrategicCoefficientService;
import com.dp.trains.services.TrainTypeService;
import com.dp.trains.ui.components.CalculatePricePerTrainLayout;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
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

    private Button add;
    private Button finalize;
    private Button doNewCalculation;
    private Button calculateFinalTax;
    private NumberField tonnage;
    private IntegerField trainNumber;
    private Select<String> trainType;
    private CalculatePricePerTrainLayout calculatePricePerTrainLayout;
    private Select<StrategicCoefficientEntity> strategicCoefficientEntityComboBox;

    @Autowired
    private TrainTypeService trainTypeService;

    @Autowired
    private SectionsService sectionsService;

    @Autowired
    private StrategicCoefficientService strategicCoefficientService;

    @Autowired
    private ServiceChargesPerTrainService serviceChargesPerTrainService;

    public CalculatePricePerTrainView() {

        calculatePricePerTrainLayout = new CalculatePricePerTrainLayout();

        HorizontalLayout baseParametersLayout = new HorizontalLayout();

        trainNumber = new IntegerField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_NUMBER));
        trainNumber.addValueChangeListener(event -> {
            calculatePricePerTrainLayout.updateTrainNumberForRows(event.getValue());
            shouldEnableAddButton();
        });

        trainType = new Select<>();
        trainType.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TRAIN_TYPE));
        trainType.addValueChangeListener(event -> shouldEnableAddButton());
        tonnage = new NumberField(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TONNAGE));
        tonnage.addValueChangeListener(event -> shouldEnableAddButton());

        baseParametersLayout.add(trainNumber, trainType, tonnage);

        HorizontalLayout buttonLayout = getButtonOptionsBar();

        strategicCoefficientEntityComboBox = new Select();
        strategicCoefficientEntityComboBox.setLabel(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_STRATEGIC_COEFFICIENT));

        HorizontalLayout kilometersSummaryLayout = getKilometersSummaryLayout();
        HorizontalLayout finalTaxLayout = getFinalTax();

        VerticalLayout footerContainer = new VerticalLayout(strategicCoefficientEntityComboBox,
                kilometersSummaryLayout, finalTaxLayout);

        footerContainer.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        calculatePricePerTrainLayout.add(buttonLayout);
        calculatePricePerTrainLayout.add(baseParametersLayout);

        getContent().add(calculatePricePerTrainLayout, footerContainer);
    }

    private void shouldEnableAddButton() {

        if (trainNumber.getValue() != null && trainType.getValue() != null &&
                !"".equals(trainType.getValue()) && tonnage.getValue() != null) {

            add.setEnabled(true);
        }
    }

    @PostConstruct
    public void populateDropDowns() {

        trainType.setItems(trainTypeService.getTrainTypes());
        strategicCoefficientEntityComboBox.setItems(strategicCoefficientService.fetch(0, 0));
        strategicCoefficientEntityComboBox.setItemLabelGenerator(StrategicCoefficientEntity::getName);
        EventBusHolder.getEventBus().register(this);
    }

    private HorizontalLayout getFinalTax() {

        HorizontalLayout finalTaxLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_FINAL_TAX)));
        finalTaxLayout.add(verticalLayout);
        return finalTaxLayout;
    }

    private HorizontalLayout getKilometersSummaryLayout() {

        H3 totalKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_KILOMETERS));

        H3 totalBruttoKilometersTitle = new H3(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_TOTAL_BRUTTO_TONNE_KILOMETERS));

        VerticalLayout totalKilometersLayout = new VerticalLayout(totalKilometersTitle);
        VerticalLayout totalBruttoTonneKilometersLayout = new VerticalLayout(totalBruttoKilometersTitle);

        HorizontalLayout kilometersSummaryFieldsLayout = new HorizontalLayout();

        kilometersSummaryFieldsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        kilometersSummaryFieldsLayout.setWidth("100%");

        kilometersSummaryFieldsLayout.add(totalKilometersLayout, totalBruttoTonneKilometersLayout);

        return kilometersSummaryFieldsLayout;
    }

    private HorizontalLayout getButtonOptionsBar() {

        add = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD), VaadinIcon.PLUS.create());
        add.setEnabled(false);
        finalize = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_BUTTON_ADD_FINAL_STATION), VaadinIcon.BAN.create());
        finalize.setEnabled(false);

        calculateFinalTax = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_CALCULATE_FINAL_TAX), VaadinIcon.CALC_BOOK.create());
        calculateFinalTax.setEnabled(false);
        doNewCalculation = new Button(getTranslation(CALCULATE_PRICE_PER_TRAIN_VIEW_DO_NEW_CALCULATION), VaadinIcon.REFRESH.create());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(add, finalize, doNewCalculation, calculateFinalTax);

        add.addClickListener(e -> {
            calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                    false, sectionsService, serviceChargesPerTrainService);

            add.setEnabled(false);
            finalize.setEnabled(false);
        });

        finalize.addClickListener(e -> {
            add.setEnabled(false);
            finalize.setEnabled(false);
            calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                    true, sectionsService, serviceChargesPerTrainService);
        });

        calculateFinalTax.addClickListener(event -> calculateFinalTax());
        doNewCalculation.addClickListener(e -> resetPageState());

        return buttonLayout;
    }

    private void calculateFinalTax() {

    }

    private void resetPageState() {

        calculatePricePerTrainLayout.resetContainer();
        strategicCoefficientEntityComboBox.setValue(null);
        trainType.setValue("");
        trainNumber.setValue(null);
        tonnage.setValue(null);
        finalize.setEnabled(false);
        add.setEnabled(false);
        calculateFinalTax.setEnabled(false);
    }

    @Subscribe
    private void handleRowDoneEvent(CPPTRowDoneEvent cpptRowDoneEvent) {

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
    private void handleFinalRowRemovedEvent(CPPTFinalRowRemovedEvent cpptFinalRowRemovedEvent) {

        this.add.setEnabled(true);
        this.finalize.setEnabled(true);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
    }
}