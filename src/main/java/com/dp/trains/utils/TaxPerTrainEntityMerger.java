package com.dp.trains.utils;

import com.dp.trains.model.dto.report.TaxPerTrainReportDto;
import com.dp.trains.model.entities.TaxPerTrainEntity;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TaxPerTrainEntityMerger {

    private final Map<Long, List<TaxPerTrainEntity>> backingMap = Maps.newLinkedHashMap();

    public TaxPerTrainEntityMerger(List<TaxPerTrainEntity> entities) {

        Long previousHash = null;

        for (int i = 0; i < entities.size(); i++) {

            TaxPerTrainEntity currentEntity = entities.get(i);

            if (previousHash == null) {

                previousHash = getHashForEntity(currentEntity);
            }

            Long currentHash = getHashForEntity(currentEntity);

            log.info("Current entity: " + currentEntity.toString() + " prev hash: " + previousHash + " curr hash: " + currentHash);

            if (Objects.equals(currentHash, previousHash) && i > 0) {

                backingMap.get(currentHash).add(currentEntity);

            } else if (backingMap.get(currentHash) != null) {

                Long reHash = hashLong(currentHash);
                this.backingMap.put(reHash, Lists.newArrayList(currentEntity));

            } else {

                this.backingMap.put(currentHash, Lists.newArrayList(currentEntity));
            }

            previousHash = currentHash;
        }

        log.info("Constructed map for report: " + backingMap.toString());
    }

    public List<TaxPerTrainReportDto> getReportDtos() {

        List<TaxPerTrainReportDto> result = Lists.newArrayList();

        for (Map.Entry<Long, List<TaxPerTrainEntity>> entry : backingMap.entrySet()) {

            List<TaxPerTrainEntity> currentList = entry.getValue();

            TaxPerTrainReportDto taxPerTrainReportDto = new TaxPerTrainReportDto();
            taxPerTrainReportDto.setKilometersOnNonElectrifiedLocalLines(0.0);
            taxPerTrainReportDto.setKilometersOnNonElectrifiedHighwayAndRegionalLines(0.0);
            taxPerTrainReportDto.setKilometersOnElectrifiedLines(0.0);
            taxPerTrainReportDto.setTax(BigDecimal.ZERO);

            String startStation = currentList.get(0).getStartStation();
            String endStation = null;

            for (TaxPerTrainEntity taxPerTrainEntity : currentList) {

                Double kmOnElectrifiedLinesDto = taxPerTrainReportDto.getKilometersOnElectrifiedLines() == null ? 0.0 : taxPerTrainReportDto.getKilometersOnElectrifiedLines();
                Double kmOnElectrifiedLinesEntity = taxPerTrainEntity.getKilometersOnElectrifiedLines() == null ? 0.0 : taxPerTrainEntity.getKilometersOnElectrifiedLines();

                Double kmOnNonElectrifiedHighwayAndRegionalLinesDto = taxPerTrainReportDto.getKilometersOnNonElectrifiedHighwayAndRegionalLines() == null ? 0.0 : taxPerTrainReportDto.getKilometersOnNonElectrifiedHighwayAndRegionalLines();
                Double kmOnNonElectrifiedHighwayAndRegionalLinesEntity = taxPerTrainEntity.getKilometersOnNonElectrifiedHighwayAndRegionalLines() == null ? 0.0 : taxPerTrainEntity.getKilometersOnNonElectrifiedHighwayAndRegionalLines();

                Double kmOnNonElectrifiedLocalLinesDto = taxPerTrainReportDto.getKilometersOnNonElectrifiedLocalLines() == null ? 0.0 : taxPerTrainReportDto.getKilometersOnNonElectrifiedLocalLines();
                Double kmOnNonElectrifiedLocalLinesEntity = taxPerTrainEntity.getKilometersOnNonElectrifiedLocalLines() == null ? 0.0 : taxPerTrainEntity.getKilometersOnNonElectrifiedLocalLines();

                taxPerTrainReportDto.setCalendarOfMovement(taxPerTrainEntity.getCalendarOfMovement());
                taxPerTrainReportDto.setIsElectrified(taxPerTrainEntity.getIsElectrified());
                taxPerTrainReportDto.setKilometersOnElectrifiedLines(kmOnElectrifiedLinesDto + kmOnElectrifiedLinesEntity);
                taxPerTrainReportDto.setKilometersOnNonElectrifiedHighwayAndRegionalLines(kmOnNonElectrifiedHighwayAndRegionalLinesDto + kmOnNonElectrifiedHighwayAndRegionalLinesEntity);
                taxPerTrainReportDto.setKilometersOnNonElectrifiedLocalLines(kmOnNonElectrifiedLocalLinesDto + kmOnNonElectrifiedLocalLinesEntity);
                taxPerTrainReportDto.setLocomotiveSeries(taxPerTrainEntity.getLocomotiveSeries());
                taxPerTrainReportDto.setLocomotiveWeight(taxPerTrainEntity.getLocomotiveWeight());
                taxPerTrainReportDto.setNotes(taxPerTrainEntity.getNotes());
                taxPerTrainReportDto.setStartStation(taxPerTrainEntity.getStartStation());
                taxPerTrainReportDto.setEndStation(taxPerTrainEntity.getEndStation());
                taxPerTrainReportDto.setStrategicCoefficient(taxPerTrainEntity.getStrategicCoefficient());
                taxPerTrainReportDto.setTotalTrainWeight(taxPerTrainEntity.getTotalTrainWeight());
                taxPerTrainReportDto.setTrainLength(taxPerTrainEntity.getTrainLength());
                taxPerTrainReportDto.setTrainWeightWithoutLocomotive(taxPerTrainEntity.getTrainWeightWithoutLocomotive());
                taxPerTrainReportDto.setTrainType(taxPerTrainEntity.getTrainType());
                taxPerTrainReportDto.setTrainNumber(taxPerTrainEntity.getTrainNumber());
                taxPerTrainReportDto.setTax(taxPerTrainReportDto.getTax().add(taxPerTrainEntity.getTax()));

                endStation = taxPerTrainEntity.getEndStation();
            }

            taxPerTrainReportDto.setStartStation(startStation);
            taxPerTrainReportDto.setEndStation(endStation);

            result.add(taxPerTrainReportDto);
        }

        log.info("Constructed map for report: " + result.toString());

        return result;
    }

    private Long hashLong(Long taxPerTrainEntity) {

        HashFunction hashFunction = Hashing.murmur3_128();

        HashCode hashCode = hashFunction.newHasher()
                .putLong(taxPerTrainEntity).hash();

        return hashCode.asLong();
    }

    private Long getHashForEntity(TaxPerTrainEntity taxPerTrainEntity) {

        HashFunction hashFunction = Hashing.murmur3_128();

        HashCode hashCode = hashFunction.newHasher()
                .putObject(taxPerTrainEntity, (Funnel<TaxPerTrainEntity>) (from, into) ->
                        into.putString(from.getTrainType(), Charsets.UTF_8)
                                .putDouble(from.getTotalTrainWeight())
                                .putInt(from.getTrainNumber())
                                .putString(from.getTrainType(), Charsets.UTF_8)
                                .putBoolean(from.getIsElectrified())
                                .putDouble(from.getLocomotiveWeight())
                                .putDouble(from.getTrainWeightWithoutLocomotive())
                                .putDouble(from.getTotalTrainWeight())
                                .putString(from.getLocomotiveSeries(), Charsets.UTF_8)).hash();

        return hashCode.asLong();
    }
}