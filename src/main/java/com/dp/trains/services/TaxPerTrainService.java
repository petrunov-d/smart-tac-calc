package com.dp.trains.services;

import com.dp.trains.exception.CodeNotFoundException;
import com.dp.trains.model.dto.CalculateFinalTaxPerTrainDto;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.model.entities.*;
import com.dp.trains.repository.TaxPerTrainRepository;
import com.google.common.collect.Lists;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxPerTrainService {

    private final String HIGHWAY_LINE = "M";
    private final String LOCAL_LINE = "L";
    private final String REGIONAL = "R";

    private final TaxPerTrainRepository taxPerTrainRepository;

    private final LineTypeService lineTypeService;
    private final UnitPriceService unitPriceService;

    @Transactional
    public void deleteByTrainNumber(Integer trainNumber) {

        this.taxPerTrainRepository.deleteAllByTrainNumber(trainNumber);
    }

    @Transactional(readOnly = true)
    public List<TaxPerTrainEntity> getByTrainNumber(Integer trainNumber) {

        return this.taxPerTrainRepository.findAllByTrainNumber(trainNumber);
    }

    @Transactional(readOnly = true)
    public Boolean hasRecordsForTrainNumber(Integer trainNumber) {

        return !this.getByTrainNumber(trainNumber).isEmpty();
    }

    @Transactional
    public CalculateFinalTaxPerTrainDto calculateFinalTaxForTrain(List<CalculateTaxPerTrainRowDataDto> allRowData,
                                                                  StrategicCoefficientEntity strategicCoefficientEntity,
                                                                  Integer trainNumber,
                                                                  String calendar,
                                                                  String notes,
                                                                  Double trainLength,
                                                                  TrainTypeEntity trainTypeEntity) {
        String stackTrace = null;

        UUID correlationId = UUID.randomUUID();
        List<TaxPerTrainEntity> records = Lists.newArrayList();
        BigDecimal finalTax = BigDecimal.ZERO;
        BigDecimal totalKilometers = BigDecimal.ZERO;
        BigDecimal totalBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForTrainKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalAdditionalCharges = BigDecimal.ZERO;
        BigDecimal partialKilometers = BigDecimal.valueOf(Long.MIN_VALUE);

        BigDecimal strategicCoefficientMultiplier = BigDecimal.ONE;

        if (strategicCoefficientEntity != null && strategicCoefficientEntity.getCoefficient() != null) {

            strategicCoefficientMultiplier = BigDecimal.valueOf(strategicCoefficientEntity.getCoefficient());
            log.info("Strategic coefficient was selected, applying multiplier -> " + strategicCoefficientMultiplier.toString());
        }

        try {

            for (CalculateTaxPerTrainRowDataDto rowDataDto : allRowData) {

                if (!rowDataDto.getSection().getDestination().getCountry().equals(CountryCode.RS.getAlpha3())) {

                    log.info("Found a segment where country is not Serbia, skipping the whole thing -> " + rowDataDto.toString());

                    continue;
                }

                BigDecimal sumOfAdditionalChargesForRow = BigDecimal.valueOf(rowDataDto
                        .getServiceChargesPerTrainEntityList().stream()
                        .map(x -> x.getServiceEntity().getUnitPrice() * x.getServiceCount())
                        .mapToDouble(x -> x).sum());

                log.info("Adding additional charges for row:" + sumOfAdditionalChargesForRow);

                totalAdditionalCharges = totalAdditionalCharges.add(sumOfAdditionalChargesForRow);

                if (rowDataDto.getSection().getRowIndex() == 1) {

                    if (!rowDataDto.getSection().getIsKeyStation()) {

                        partialKilometers = BigDecimal.valueOf(rowDataDto.getSection().getKilometersBetweenStations());
                    }

                    log.info("Skipping start station -> " + rowDataDto.toString());
                    continue;
                }

                log.info("Row Data:" + rowDataDto.toString());

                List<UnitPriceEntity> unitPricesForSection = findUnitPricesForSection(trainTypeEntity, rowDataDto);

                UnitPriceEntity unitPriceForTrainKilometers = unitPricesForSection.get(0);
                UnitPriceEntity unitPriceForBruttoTonneKilometers = unitPricesForSection.get(1);

                log.info("Unit price for TK for this section: " + unitPriceForTrainKilometers.toString());
                log.info("Unit price for BTK for this section: " + unitPriceForTrainKilometers.toString());

                BigDecimal trainKilometersForSection;
                BigDecimal bruttoTonneKilometersForSection;

                if (!partialKilometers.equals(BigDecimal.valueOf(Long.MIN_VALUE))) {

                    Double partialKms = rowDataDto.getSection().getKilometersBetweenStations() - partialKilometers.doubleValue();

                    trainKilometersForSection = BigDecimal.valueOf(rowDataDto.getSection().getKilometersBetweenStations() - partialKms);
                    bruttoTonneKilometersForSection = BigDecimal.valueOf(rowDataDto.getTonnage() * (rowDataDto.getSection().getKilometersBetweenStations() - partialKms));

                    partialKilometers = BigDecimal.valueOf(Long.MIN_VALUE);

                } else {

                    trainKilometersForSection = BigDecimal.valueOf(rowDataDto.getSection().getKilometersBetweenStations());
                    bruttoTonneKilometersForSection = BigDecimal.valueOf(rowDataDto.getTonnage() * rowDataDto.getSection().getKilometersBetweenStations());
                }

                log.info("BTK for section:" + bruttoTonneKilometersForSection.toString());
                log.info("TK for section:" + trainKilometersForSection.toString());

                BigDecimal trainKilometersPriceForSection = trainKilometersForSection.multiply(unitPriceForTrainKilometers.getUnitPrice());
                BigDecimal bruttoTonneKilometersPriceForSection = bruttoTonneKilometersForSection.multiply(unitPriceForBruttoTonneKilometers.getUnitPrice());

                BigDecimal kilometersPart = trainKilometersPriceForSection.add(bruttoTonneKilometersPriceForSection);
                BigDecimal timesSC = kilometersPart.multiply(strategicCoefficientMultiplier);
                BigDecimal plusCharges = timesSC.add(totalAdditionalCharges);

                totalKilometers = totalKilometers.add(trainKilometersForSection);
                totalBruttoTonneKilometers = totalBruttoTonneKilometers.add(bruttoTonneKilometersForSection);
                records.add(getRecordForSection(rowDataDto, strategicCoefficientMultiplier, trainNumber, calendar, notes, trainLength, correlationId, trainTypeEntity, plusCharges));

                totalTaxForTrainKilometers = totalTaxForTrainKilometers.add(trainKilometersPriceForSection);
                totalTaxForBruttoTonneKilometers = totalTaxForBruttoTonneKilometers.add(bruttoTonneKilometersPriceForSection);
            }

            BigDecimal totalSumOfTrainKilometersAndBruttoTonneKilometers = totalTaxForTrainKilometers.add(totalTaxForBruttoTonneKilometers);
            BigDecimal totalSumTimesStrategicCoefficient = totalSumOfTrainKilometersAndBruttoTonneKilometers.multiply(strategicCoefficientMultiplier);

            finalTax = totalSumTimesStrategicCoefficient.add(totalAdditionalCharges);

        } catch (Exception e) {

            log.error("Error calculating final tax: ", e);

            stackTrace = ExceptionUtils.getStackTrace(e);
        }

        this.taxPerTrainRepository.saveAll(records);

        return CalculateFinalTaxPerTrainDto.builder()
                .totalKilometers(totalKilometers)
                .totalBruttoTonneKilometers(totalBruttoTonneKilometers)
                .finalTax(finalTax)
                .stackTrace(stackTrace)
                .build();
    }

    private TaxPerTrainEntity getRecordForSection(CalculateTaxPerTrainRowDataDto rowDataDto,
                                                  BigDecimal strategicCoefficientMultiplier,
                                                  Integer trainNumber,
                                                  String calendar,
                                                  String notes,
                                                  Double trainLength,
                                                  UUID correlationId,
                                                  TrainTypeEntity trainTypeEntity,
                                                  BigDecimal tax) {

        Boolean isElectrified = rowDataDto.getSection().getIsElectrified();

        return TaxPerTrainEntity
                .builder()
                .calendarOfMovement(calendar)
                .correlationId(correlationId)
                .isElectrified(isElectrified)
                .startStation(rowDataDto.getSection().getSource().getStation())
                .endStation(rowDataDto.getSection().getDestination().getStation())
                .strategicCoefficient(BigDecimal.ONE.equals(strategicCoefficientMultiplier) ? null : strategicCoefficientMultiplier)
                .trainNumber(trainNumber)
                .notes(notes)
                .tax(tax)
                .trainLength(trainLength)
                .trainType(trainTypeEntity.getName())
                .locomotiveWeight(rowDataDto.getLocomotiveWeight())
                .trainWeightWithoutLocomotive(rowDataDto.getTonnage())
                .locomotiveSeries(rowDataDto.getLocomotiveSeries())
                .totalTrainWeight(rowDataDto.getLocomotiveWeight() + rowDataDto.getTonnage())
                .kilometersOnElectrifiedLines(isElectrified ? rowDataDto.getSection().getKilometersBetweenStations() : null)
                .kilometersOnNonElectrifiedHighwayAndRegionalLines(HIGHWAY_LINE.equals(rowDataDto.getSection().getTypeOfLine()) ? rowDataDto.getSection().getKilometersBetweenStations() : null)
                .kilometersOnNonElectrifiedLocalLines(LOCAL_LINE.equals(rowDataDto.getSection().getTypeOfLine()) ? rowDataDto.getSection().getKilometersBetweenStations() : null)
                .build();
    }

    private List<UnitPriceEntity> findUnitPricesForSection(TrainTypeEntity trainTypeEntity,
                                                           CalculateTaxPerTrainRowDataDto calculateTaxPerTrainRowDataDto) throws CodeNotFoundException {

        List<UnitPriceEntity> unitPriceEntities = Lists.newArrayList();

        UnitPriceEntity unitPriceForKilometers = getForCode(false,
                trainTypeEntity, calculateTaxPerTrainRowDataDto);

        UnitPriceEntity unitPriceForBruttoTonneKilometers = getForCode(true,
                trainTypeEntity, calculateTaxPerTrainRowDataDto);

        unitPriceEntities.add(unitPriceForKilometers);
        unitPriceEntities.add(unitPriceForBruttoTonneKilometers);

        return unitPriceEntities;
    }

    private UnitPriceEntity getForCode(boolean isTrainKilometers, TrainTypeEntity trainTypeEntity,
                                       CalculateTaxPerTrainRowDataDto calculateTaxPerTrainRowDataDto) throws CodeNotFoundException {

        LineTypeEntity lineTypeEntity =
                lineTypeService.getByType(calculateTaxPerTrainRowDataDto.getSection().getTypeOfLine());

        Integer lineCode = Integer.valueOf(lineTypeEntity.getCode());
        Integer electrifiedCode = calculateTaxPerTrainRowDataDto.getSection().getIsElectrified() ? 1 : 0;
        Integer trainTypeCode = Integer.valueOf(trainTypeEntity.getCode());
        Integer kilometersCode = isTrainKilometers ? 0 : 1;

        String code = String.format("%d%d%d%d", lineCode, electrifiedCode, trainTypeCode, kilometersCode);

        log.info("Querying unit prices for code:" + code);

        UnitPriceEntity unitPriceEntity = unitPriceService.findByCode(code);

        if (unitPriceEntity == null) {

            throw new CodeNotFoundException("Cound not find unit price entity for code: " + code);
        }

        return unitPriceService.findByCode(code);
    }
}
