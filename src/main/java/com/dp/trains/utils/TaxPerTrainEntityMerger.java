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

    private Map<Long, List<TaxPerTrainEntity>> backingMap = Maps.newHashMap();

    public TaxPerTrainEntityMerger(List<TaxPerTrainEntity> entities) {

        Long previousHash = null;

        for (int i = 0; i < entities.size(); i++) {

            TaxPerTrainEntity currentEntity = entities.get(i);

            if (previousHash == null) {

                previousHash = getHashForEntity(currentEntity);
            }

            Long currentHash = getHashForEntity(currentEntity);

            if (Objects.equals(currentHash, previousHash) && i > 0) {

                List<TaxPerTrainEntity> oldList = backingMap.get(currentHash);
                oldList.add(currentEntity);
                backingMap.put(currentHash, oldList);

            } else if (backingMap.get(currentHash) != null) {

                this.backingMap.put(hashLong(currentHash), Lists.newArrayList(currentEntity));

            } else {

                this.backingMap.put(currentHash, Lists.newArrayList(currentEntity));
            }

            previousHash = currentHash;
        }
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

            for (TaxPerTrainEntity taxPerTrainEntity : currentList) {

                taxPerTrainReportDto.setCalendarOfMovement(taxPerTrainEntity.getCalendarOfMovement());
                taxPerTrainReportDto.setIsElectrified(taxPerTrainEntity.getIsElectrified());
                taxPerTrainReportDto.setKilometersOnElectrifiedLines(
                        taxPerTrainReportDto.getKilometersOnElectrifiedLines()
                                + taxPerTrainEntity.getKilometersOnElectrifiedLines());
                taxPerTrainReportDto.setKilometersOnNonElectrifiedHighwayAndRegionalLines(
                        taxPerTrainReportDto.getKilometersOnNonElectrifiedHighwayAndRegionalLines()
                                + taxPerTrainEntity.getKilometersOnNonElectrifiedHighwayAndRegionalLines()
                );
                taxPerTrainReportDto.setKilometersOnNonElectrifiedLocalLines(
                        taxPerTrainReportDto.getKilometersOnNonElectrifiedLocalLines()
                                + taxPerTrainEntity.getKilometersOnNonElectrifiedLocalLines());
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
            }

            result.add(taxPerTrainReportDto);
        }

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
                                .putString(from.getLocomotiveSeries(), Charsets.UTF_8)).hash();

        return hashCode.asLong();
    }
}