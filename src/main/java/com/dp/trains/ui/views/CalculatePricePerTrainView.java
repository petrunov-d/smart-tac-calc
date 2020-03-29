package com.dp.trains.ui.views;

import com.dp.trains.model.entities.StrategicCoefficientEntity;
import com.dp.trains.services.SectionsService;
import com.dp.trains.services.ServiceChargesPerTrainService;
import com.dp.trains.services.StrategicCoefficientService;
import com.dp.trains.services.TrainTypeService;
import com.dp.trains.ui.components.CalculatePricePerTrainLayout;
import com.dp.trains.ui.layout.MainLayout;
import com.dp.trains.utils.EventBusHolder;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

@Slf4j
@UIScope
@SpringComponent
@Route(value = CalculatePricePerTrainView.NAV_CALCULATE_PRICE_PER_TRAIN, layout = MainLayout.class)
public class CalculatePricePerTrainView extends Composite<Div> implements BeforeLeaveObserver {

    static final String NAV_CALCULATE_PRICE_PER_TRAIN = "calculate_price_per_train";

    private Button add;
    private Button finalize;
    private NumberField tonnage;
    private IntegerField trainNumber;
    private ComboBox<String> trainType;
    private CalculatePricePerTrainLayout calculatePricePerTrainLayout;
    private ComboBox<StrategicCoefficientEntity> strategicCoefficientEntityComboBox;

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

        trainNumber = new IntegerField("Train Number");
        trainNumber.addValueChangeListener(event -> {
            calculatePricePerTrainLayout.updateTrainNumberForRows(event.getValue());
            shouldEnableAddButton();
        });

        trainType = new ComboBox<>("Train Type");
        trainType.addValueChangeListener(event -> shouldEnableAddButton());
        tonnage = new NumberField("Tonnage");
        tonnage.addValueChangeListener(event -> shouldEnableAddButton());

        baseParametersLayout.add(trainNumber, trainType, tonnage);

        HorizontalLayout buttonLayout = getButtonOptionsBar();

        strategicCoefficientEntityComboBox = new ComboBox<>("Strategic Coefficient: ");

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
        verticalLayout.add(new H3("Final Tax: "));
        finalTaxLayout.add(verticalLayout);
        return finalTaxLayout;
    }

    private HorizontalLayout getKilometersSummaryLayout() {

        H3 totalKilometersTitle = new H3("Total Kilometers: ");

        H3 totalBruttoKilometersTitle = new H3("Total Brutto Tonne Kilometers: ");

        VerticalLayout totalKilometersLayout = new VerticalLayout(totalKilometersTitle);
        VerticalLayout totalBruttoTonneKilometersLayout = new VerticalLayout(totalBruttoKilometersTitle);

        HorizontalLayout kilometersSummaryFieldsLayout = new HorizontalLayout();

        kilometersSummaryFieldsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        kilometersSummaryFieldsLayout.setWidth("100%");

        kilometersSummaryFieldsLayout.add(totalKilometersLayout, totalBruttoTonneKilometersLayout);

        return kilometersSummaryFieldsLayout;
    }

    private HorizontalLayout getButtonOptionsBar() {

        add = new Button("Add", VaadinIcon.PLUS.create());
        add.setEnabled(false);
        finalize = new Button("Add Final Station", VaadinIcon.BAN.create());

        Button calculateFinalTax = new Button("Calculate Final Tax", VaadinIcon.CALC_BOOK.create());
        calculateFinalTax.setEnabled(false);
        Button clear = new Button("Do new Calculation", VaadinIcon.REFRESH.create());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(add, finalize, clear, calculateFinalTax);

        add.addClickListener(e -> calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                false, sectionsService, serviceChargesPerTrainService));

        finalize.addClickListener(e -> {
            add.setEnabled(false);
            finalize.setEnabled(false);
            calculatePricePerTrainLayout.addRow(trainNumber.getValue(),
                    true, sectionsService, serviceChargesPerTrainService);
        });

        clear.addClickListener(e -> resetPageState());

        return buttonLayout;
    }

    private void calculateFinalTax() {

    }

    private void resetPageState() {

        calculatePricePerTrainLayout.resetContainer();
        add.setEnabled(false);
        finalize.setEnabled(true);
        strategicCoefficientEntityComboBox.setValue(null);
        trainType.setValue("");
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
    }
}