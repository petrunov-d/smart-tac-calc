package com.dp.trains.services;

import com.dp.trains.exception.CodeNotFoundException;
import com.dp.trains.model.dto.CalculateFinalTaxPerTrainDto;
import com.dp.trains.model.dto.CalculateTaxPerTrainRowDataDto;
import com.dp.trains.model.dto.ExcelImportDto;
import com.dp.trains.model.dto.TaxForServicesPerTrainDto;
import com.dp.trains.model.entities.*;
import com.dp.trains.model.viewmodels.PreviousYearCopyingResultViewModel;
import com.dp.trains.repository.TaxForServicesPerTrainRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class TaxForServicesPerTrainService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;

    private final UnitPriceService unitPriceService;
    private final LineTypeService lineTypeService;

    private final TaxForServicesPerTrainRepository taxForServicesPerTrainRepository;

    @Qualifier("taxForServicesPerTrainMapper")
    private final DefaultDtoEntityMapperService<TaxForServicesPerTrainDto, TaxForServicesPerTrainEntity> taxForServicesPerTrainMapper;

    @Transactional
    public void add(Collection<TaxForServicesPerTrainDto> trainTypeDto) {

        Collection<TaxForServicesPerTrainEntity> taxForServicesPerTrainDtos = taxForServicesPerTrainMapper.mapEntities(trainTypeDto);

        taxForServicesPerTrainRepository.saveAll(taxForServicesPerTrainDtos);
    }

    @Override
    @Transactional
    public void importFromExcel(List<? extends ExcelImportDto> excelImportDto) {

        this.add((Collection<TaxForServicesPerTrainDto>) excelImportDto);
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {

        return (int) taxForServicesPerTrainRepository.count();
    }

    @Override
    @Transactional
    public void deleteAll() {

        taxForServicesPerTrainRepository.deleteAll();
    }

    @Override
    public PreviousYearCopyingResultViewModel copyFromPreviousYear(Integer previousYear) {
        return null;
    }

    @Override
    public int countByYear(int year) {
        return -1;
    }

    @Override
    public String getDisplayName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public CalculateFinalTaxPerTrainDto calculateFinalTaxForTrain(List<CalculateTaxPerTrainRowDataDto> allRowData,
                                                                  StrategicCoefficientEntity strategicCoefficientEntity,
                                                                  Integer trainNumber,
                                                                  TrainTypeEntity trainTypeEntity) {
        String stackTrace = null;

        BigDecimal finalTax = BigDecimal.ZERO;
        BigDecimal totalKilometers = BigDecimal.ZERO;
        BigDecimal totalBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForTrainKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalAdditionalCharges = BigDecimal.ZERO;
        BigDecimal partialKilometers = BigDecimal.valueOf(Long.MIN_VALUE);

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

                totalKilometers = totalKilometers.add(trainKilometersForSection);
                totalBruttoTonneKilometers = totalBruttoTonneKilometers.add(bruttoTonneKilometersForSection);

                totalTaxForTrainKilometers = totalTaxForTrainKilometers.add(trainKilometersForSection.multiply(unitPriceForTrainKilometers.getUnitPrice()));

                totalTaxForBruttoTonneKilometers = totalTaxForBruttoTonneKilometers.add(bruttoTonneKilometersForSection.multiply(unitPriceForBruttoTonneKilometers.getUnitPrice()));
            }

            BigDecimal strategicCoefficientMultiplier = BigDecimal.ONE;

            if (strategicCoefficientEntity != null && strategicCoefficientEntity.getCoefficient() != null) {

                strategicCoefficientMultiplier = BigDecimal.valueOf(strategicCoefficientEntity.getCoefficient());
                log.info("Strategic coefficient was selected, applying multiplier -> " + strategicCoefficientMultiplier.toString());
            }

            BigDecimal totalSumOfTrainKilometersAndBruttoTonneKilometers = totalTaxForTrainKilometers.add(totalTaxForBruttoTonneKilometers);
            BigDecimal totalSumTimesStrategicCoefficient = totalSumOfTrainKilometersAndBruttoTonneKilometers.multiply(strategicCoefficientMultiplier);

            finalTax = totalSumTimesStrategicCoefficient.add(totalAdditionalCharges);

        } catch (Exception e) {

            log.error("Error calculating final tax: ", e);

            stackTrace = ExceptionUtils.getStackTrace(e);
        }

        return CalculateFinalTaxPerTrainDto.builder()
                .totalKilometers(totalKilometers)
                .totalBruttoTonneKilometers(totalBruttoTonneKilometers)
                .finalTax(finalTax)
                .stackTrace(stackTrace)
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

        Integer lineCode = lineTypeEntity.getCode();
        Integer electrifiedCode = calculateTaxPerTrainRowDataDto.getSection().getIsElectrified() ? 1 : 0;
        Integer trainTypeCode = trainTypeEntity.getCode();
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