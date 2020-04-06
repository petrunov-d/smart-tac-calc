package com.dp.trains.services;

import com.dp.trains.exception.CodeNotFoundException;
import com.dp.trains.model.dto.*;
import com.dp.trains.model.entities.*;
import com.dp.trains.repository.TaxForServicesPerTrainRepository;
import com.dp.trains.utils.mapper.impl.DefaultDtoEntityMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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
public class TaxForServicesPerTrainService implements BaseImportService {

    private final ObjectMapper defaultObjectMapper;
    private final TaxForServicesPerTrainRepository taxForServicesPerTrainRepository;
    private final FinancialDataService financialDataService;
    private final UnitPriceService unitPriceService;
    private final LineTypeService lineTypeService;

    @Qualifier("taxForServicesPerTrainMapper")
    private final DefaultDtoEntityMapperService<TaxForServicesPerTrainDto,
            TaxForServicesPerTrainEntity> taxForServicesPerTrainMapper;

    @Transactional
    public void add(Collection<TaxForServicesPerTrainDto> trainTypeDto) {

        Collection<TaxForServicesPerTrainEntity> taxForServicesPerTrainDtos =
                taxForServicesPerTrainMapper.mapEntities(trainTypeDto);

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
    public PreviousYearCopyingResultDto copyFromPreviousYear(Integer previousYear) {
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
                                                                  Integer trainNumber, TrainTypeEntity trainTypeEntity) {

        boolean errorInCalculation = false;
        String stackTrace = null;

        BigDecimal finalTax = BigDecimal.ZERO;
        BigDecimal totalKilometers = BigDecimal.ZERO;
        BigDecimal totalBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForTrainKilometers = BigDecimal.ZERO;
        BigDecimal totalTaxForBruttoTonneKilometers = BigDecimal.ZERO;
        BigDecimal totalAdditionalCharges = BigDecimal.ZERO;

        try {

            int i = 0;

            for (CalculateTaxPerTrainRowDataDto rowDataDto : allRowData) {

                if (!rowDataDto.getSection().getDestination().getCountry().equals("SRB")) {

                    log.info("Found segment where country is not Serbia, skipping->" + rowDataDto.toString());
                    continue;
                }

                Double partialKilometers = Double.NEGATIVE_INFINITY;

                if (i == 0 || (i == allRowData.size() - 1)) {

                    if (rowDataDto.getSection().getSubSectionDtoList().stream().anyMatch(SubSectionDto::getIsSelected)) {

                        SubSectionDto subSectionDto = rowDataDto.getSection()
                                .getSubSectionDtoList()
                                .stream()
                                .filter(SubSectionDto::getIsSelected)
                                .findFirst().get();

                        partialKilometers = rowDataDto.getSection().getKilometersBetweenStations()
                                - subSectionDto.getKilometers();

                        log.info("Selected segment is first or last and selected station is not key, partial kms = "
                                + partialKilometers + " " +
                                subSectionDto.toString() + " " + rowDataDto.toString());
                    }
                }

                log.info("Row Data:" + rowDataDto.toString());

                List<UnitPriceEntity> unitPricesForSection = findUnitPricesForSection(trainTypeEntity, rowDataDto);

                UnitPriceEntity unitPriceForTrainKilometers = unitPricesForSection.get(0);

                log.info("Unit price for TK for section:" + unitPriceForTrainKilometers.toString());

                UnitPriceEntity unitPriceForBruttoTonneKilometers = unitPricesForSection.get(1);

                log.info("Unit price for BTK for section:" + unitPriceForTrainKilometers.toString());

                BigDecimal trainKilometersForSection;

                BigDecimal bruttoTonneKilometersForSection;

                if (partialKilometers == Double.NEGATIVE_INFINITY) {

                    trainKilometersForSection = BigDecimal.valueOf(rowDataDto.getSection().getKilometersBetweenStations());
                    bruttoTonneKilometersForSection = BigDecimal.valueOf(rowDataDto.getTonnage() * rowDataDto.getSection().getKilometersBetweenStations());

                } else {

                    trainKilometersForSection = BigDecimal.valueOf(partialKilometers);
                    bruttoTonneKilometersForSection = BigDecimal.valueOf(rowDataDto.getTonnage() * partialKilometers);
                }

                log.info("BTK for section:" + trainKilometersForSection.toString());

                totalKilometers = totalKilometers.add(trainKilometersForSection);
                totalBruttoTonneKilometers = totalBruttoTonneKilometers.add(bruttoTonneKilometersForSection);

                totalTaxForTrainKilometers = totalTaxForTrainKilometers.add(trainKilometersForSection.multiply(
                        BigDecimal.valueOf(unitPriceForTrainKilometers.getUnitPrice())));

                totalTaxForBruttoTonneKilometers = totalTaxForBruttoTonneKilometers.add(bruttoTonneKilometersForSection.multiply(
                        BigDecimal.valueOf(unitPriceForBruttoTonneKilometers.getUnitPrice())));

                BigDecimal sumOfAdditionalChargesForRow = BigDecimal.valueOf(
                        rowDataDto.getServiceChargesPerTrainEntityList().stream()
                                .map(x -> x.getServiceEntity().getUnitPrice() * x.getServiceCount())
                                .mapToDouble(x -> x)
                                .sum());

                totalAdditionalCharges = totalAdditionalCharges.add(sumOfAdditionalChargesForRow);
                i++;
            }

            BigDecimal strategicCoefficientMultiplier = BigDecimal.ONE;

            if (strategicCoefficientEntity != null && strategicCoefficientEntity.getCoefficient() != null) {

                strategicCoefficientMultiplier = BigDecimal.valueOf(strategicCoefficientEntity.getCoefficient());
            }

            BigDecimal totalSumOfTrainKilometersAndBruttoTonneKilometers = totalTaxForTrainKilometers.add(totalTaxForBruttoTonneKilometers);

            BigDecimal totalSumTimesStrategicCoefficient = totalSumOfTrainKilometersAndBruttoTonneKilometers.multiply(strategicCoefficientMultiplier);

            finalTax = totalSumTimesStrategicCoefficient.add(totalAdditionalCharges);

        } catch (Exception e) {

            log.error("Error calculating final tax: ", e);
            errorInCalculation = true;

            stackTrace = ExceptionUtils.getStackTrace(e);
        }

        return CalculateFinalTaxPerTrainDto
                .builder()
                .errorInCalculation(errorInCalculation)
                .totalKilometers(totalKilometers)
                .totalBruttoTonneKilometers(totalBruttoTonneKilometers)
                .finalTax(finalTax)
                .stackTrace(stackTrace)
                .build();
    }

    private List<UnitPriceEntity> findUnitPricesForSection(
            TrainTypeEntity trainTypeEntity, CalculateTaxPerTrainRowDataDto calculateTaxPerTrainRowDataDto)
            throws CodeNotFoundException {

        List<UnitPriceEntity> unitPriceEntities = Lists.newArrayList();

        UnitPriceEntity unitPriceForKilometers = getForCode(false,
                trainTypeEntity, calculateTaxPerTrainRowDataDto);

        UnitPriceEntity unitPriceForBruttoTonneKilometers = getForCode(true,
                trainTypeEntity, calculateTaxPerTrainRowDataDto);

        unitPriceEntities.add(unitPriceForKilometers);
        unitPriceEntities.add(unitPriceForBruttoTonneKilometers);

        return unitPriceEntities;
    }

    private UnitPriceEntity getForCode(boolean isTrainKilometers,
                                       TrainTypeEntity trainTypeEntity,
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