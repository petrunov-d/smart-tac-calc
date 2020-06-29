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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final SectionsService sectionsService;

    @Transactional
    public void deleteByTrainNumber(Integer trainNumber) {

        this.taxPerTrainRepository.deleteAllByTrainNumber(trainNumber);
        this.taxPerTrainRepository.flush();
    }

    @Transactional(readOnly = true)
    public Integer getCountByTrainNumber(Integer trainNumber) {

        return taxPerTrainRepository.countAllByTrainNumber(trainNumber);
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

        BigDecimal strategicCoefficientMultiplier = BigDecimal.ONE;

        if (strategicCoefficientEntity != null && strategicCoefficientEntity.getCoefficient() != null) {

            strategicCoefficientMultiplier = BigDecimal.valueOf(strategicCoefficientEntity.getCoefficient());
            log.info("Strategic coefficient was selected, applying multiplier -> " + strategicCoefficientMultiplier.toString());
        }

        try {

            for (CalculateTaxPerTrainRowDataDto rowDataDto : allRowData) {

                if (rowDataDto.getSection() == null || rowDataDto.getSection().getOriginalDestination() == null ||
                        rowDataDto.getSection().getOriginalDestination().getCountry() == null ||
                        !rowDataDto.getSection().getOriginalDestination().getCountry().equals(CountryCode.RS.getAlpha3())) {

                    log.info("Found a segment where country is not Serbia, skipping the whole thing -> " + rowDataDto.toString());
                    continue;
                }

                BigDecimal sumOfAdditionalChargesForRow = getSumOfAdditionalChargesForRow(rowDataDto);

                log.info("Adding additional charges for row:" + sumOfAdditionalChargesForRow);

                totalAdditionalCharges = totalAdditionalCharges.add(sumOfAdditionalChargesForRow);

                log.info("Skipping start station -> " + rowDataDto.toString());
                log.info("Row Data:" + rowDataDto.toString());

                List<UnitPriceEntity> unitPricesForSection = findUnitPricesForSection(trainTypeEntity, rowDataDto);

                UnitPriceEntity unitPriceForTrainKilometers = unitPricesForSection.get(1);
                UnitPriceEntity unitPriceForBruttoTonneKilometers = unitPricesForSection.get(0);

                log.info("Unit price for TK for this section: " + unitPriceForTrainKilometers.toString());
                log.info("Unit price for BTK for this section: " + unitPriceForTrainKilometers.toString());

                BigDecimal trainKilometersForSection;
                BigDecimal bruttoTonneKilometersForSection;

                Double kilometersBetweenStations = rowDataDto.getSection().getKilometersBetweenStations();

                trainKilometersForSection = BigDecimal.valueOf(kilometersBetweenStations);
                bruttoTonneKilometersForSection = BigDecimal.valueOf(rowDataDto.getTonnage() * kilometersBetweenStations);

                log.info("BTK for section:" + bruttoTonneKilometersForSection.toString());
                log.info("TK for section:" + trainKilometersForSection.toString());

                BigDecimal trainKilometersPriceForSection = trainKilometersForSection.multiply(unitPriceForTrainKilometers.getUnitPrice());
                BigDecimal bruttoTonneKilometersPriceForSection = bruttoTonneKilometersForSection.multiply(unitPriceForBruttoTonneKilometers.getUnitPrice());

                BigDecimal kilometersPart = trainKilometersPriceForSection.add(bruttoTonneKilometersPriceForSection);
                BigDecimal timesSC = kilometersPart.multiply(strategicCoefficientMultiplier);
                BigDecimal plusCharges = timesSC.add(totalAdditionalCharges);

                totalKilometers = totalKilometers.add(trainKilometersForSection);
                totalBruttoTonneKilometers = totalBruttoTonneKilometers.add(bruttoTonneKilometersForSection);

                TaxPerTrainEntity recordForSection = getRecordForSection(rowDataDto, strategicCoefficientMultiplier,
                        trainNumber, calendar, notes, trainLength,
                        correlationId, trainTypeEntity, kilometersBetweenStations, plusCharges);

                records.add(recordForSection);

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

        taxPerTrainRepository.saveAll(records);

        this.taxPerTrainRepository.flush();

        return CalculateFinalTaxPerTrainDto.builder()
                .totalKilometers(totalKilometers)
                .totalBruttoTonneKilometers(totalBruttoTonneKilometers)
                .finalTax(finalTax)
                .stackTrace(stackTrace)
                .build();
    }

    private BigDecimal getSumOfAdditionalChargesForRow(CalculateTaxPerTrainRowDataDto rowDataDto) {

        return BigDecimal.valueOf(rowDataDto
                .getServiceChargesPerTrainEntityList()
                .stream()
                .map(x -> x.getServiceEntity().getUnitPrice() * x.getServiceCount())
                .mapToDouble(x -> x).sum());
    }

    private TaxPerTrainEntity getRecordForSection(CalculateTaxPerTrainRowDataDto rowDataDto,
                                                  BigDecimal strategicCoefficientMultiplier,
                                                  Integer trainNumber,
                                                  String calendar,
                                                  String notes,
                                                  Double trainLength,
                                                  UUID correlationId,
                                                  TrainTypeEntity trainTypeEntity,
                                                  Double kilometersBetweenStations,
                                                  BigDecimal tax) {

        Boolean isElectrified = rowDataDto.getSection().getIsElectrified();

        TaxPerTrainEntity taxPerTrainEntity = TaxPerTrainEntity
                .builder()
                .calendarOfMovement(calendar)
                .correlationId(correlationId)
                .isElectrified(isElectrified)
                .startStation(rowDataDto.getSection().getCurrentSource().getStation())
                .endStation(rowDataDto.getSection().getCurrentDestination().getStation())
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
                .build();

        if (isElectrified) {

            taxPerTrainEntity.setKilometersOnElectrifiedLines(kilometersBetweenStations);

        } else if (HIGHWAY_LINE.equals(rowDataDto.getSection().getTypeOfLine())) {

            taxPerTrainEntity.setKilometersOnNonElectrifiedHighwayAndRegionalLines(kilometersBetweenStations);

        } else if (LOCAL_LINE.equals(rowDataDto.getSection().getTypeOfLine())) {

            taxPerTrainEntity.setKilometersOnNonElectrifiedLocalLines(kilometersBetweenStations);
        }

        return taxPerTrainEntity;
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

        LineTypeEntity lineTypeEntity = lineTypeService.getByType(calculateTaxPerTrainRowDataDto.getSection().getTypeOfLine());

        int lineCode = Integer.parseInt(lineTypeEntity.getCode());
        int electrifiedCode = calculateTaxPerTrainRowDataDto.getSection().getIsElectrified() ? 1 : 0;
        Integer trainTypeCode = Integer.valueOf(trainTypeEntity.getCode());
        Integer kilometersCode = isTrainKilometers ? 0 : 1;

        if ((lineTypeEntity.getLineType().equals(REGIONAL) || lineTypeEntity.getLineType().equals(HIGHWAY_LINE)) &&
                electrifiedCode == 0) {

            lineCode = 1;

        } else if ((lineTypeEntity.getLineType().equals(REGIONAL) || lineTypeEntity.getLineType().equals(HIGHWAY_LINE)) &&
                electrifiedCode == 1) {

            lineCode = 0;
        }

        String code = String.format("%d%d%d%d", lineCode, electrifiedCode, trainTypeCode, kilometersCode);

        log.info("Querying unit prices for code:" + code);

        UnitPriceEntity unitPriceEntity = unitPriceService.findByCode(code);

        if (unitPriceEntity == null) {

            throw new CodeNotFoundException("Cound not find unit price entity for code: " + code);
        }

        return unitPriceService.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Set<Integer> getAllTrainNumbersWithCalculatedTaxes() {

        return this.taxPerTrainRepository.findAll().stream()
                .map(TaxPerTrainEntity::getTrainNumber)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}