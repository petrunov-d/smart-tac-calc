package com.dp.trains.utils;

import com.dp.trains.model.entities.TaxPerTrainEntity;
import com.dp.trains.model.helpers.TaxPerTrainHashHolder;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TaxPerTrainEntityMerger {

    private Map<TaxPerTrainHashHolder, List<TaxPerTrainEntity>> backingMap = Maps.newHashMap();

    public TaxPerTrainEntityMerger(List<TaxPerTrainEntity> entities) {

        Long previousHash = null;
        int previousIndex = 0;

        for (int i = 0; i < entities.size(); i++) {

            TaxPerTrainEntity currentEntity = entities.get(i);

            if (previousHash == null) {

                previousHash = getHashForEntity(currentEntity);
            }

            Long current = getHashForEntity(currentEntity);

            if (Objects.equals(current, previousHash)) {

                TaxPerTrainHashHolder testKey = new TaxPerTrainHashHolder(previousIndex, current);

                if (areSequential(getKey(backingMap.keySet(), new TaxPerTrainHashHolder(previousIndex, current)), i)) {

                    List<TaxPerTrainEntity> oldList = backingMap.get(testKey);
                    oldList.add(currentEntity);
                    backingMap.put(new TaxPerTrainHashHolder(i, current), oldList);

                } else {

                    TaxPerTrainHashHolder taxPerTrainHashHolder = new TaxPerTrainHashHolder(i, current);
                    this.backingMap.put(taxPerTrainHashHolder, Lists.newArrayList(currentEntity));
                }

            } else {

                TaxPerTrainHashHolder taxPerTrainHashHolder = new TaxPerTrainHashHolder(i, current);
                this.backingMap.put(taxPerTrainHashHolder, Lists.newArrayList(currentEntity));
            }

            previousIndex = i;
            previousHash = current;
        }
    }

    public List<TaxPerTrainEntity> getReportDtos() {

        List<TaxPerTrainEntity> result = Lists.newArrayList();

        for (Map.Entry<TaxPerTrainHashHolder, List<TaxPerTrainEntity>> entry : backingMap.entrySet()) {

            List<TaxPerTrainEntity> currentList = entry.getValue();

            TaxPerTrainEntity taxPerTrainEntityMerged = new TaxPerTrainEntity();
            taxPerTrainEntityMerged.setKilometersOnNonElectrifiedLocalLines(0.0);
            taxPerTrainEntityMerged.setKilometersOnNonElectrifiedHighwayAndRegionalLines(0.0);
            taxPerTrainEntityMerged.setKilometersOnElectrifiedLines(0.0);
            taxPerTrainEntityMerged.setTax(BigDecimal.ZERO);

            for (TaxPerTrainEntity taxPerTrainEntity : currentList) {

                taxPerTrainEntityMerged.setCalendarOfMovement(taxPerTrainEntity.getCalendarOfMovement());
                taxPerTrainEntityMerged.setCorrelationId(taxPerTrainEntity.getCorrelationId());
                taxPerTrainEntityMerged.setIsElectrified(taxPerTrainEntity.getIsElectrified());
                taxPerTrainEntityMerged.setKilometersOnElectrifiedLines(
                        taxPerTrainEntityMerged.getKilometersOnElectrifiedLines()
                                + taxPerTrainEntity.getKilometersOnElectrifiedLines());
                taxPerTrainEntityMerged.setKilometersOnNonElectrifiedHighwayAndRegionalLines(
                        taxPerTrainEntityMerged.getKilometersOnNonElectrifiedHighwayAndRegionalLines()
                                + taxPerTrainEntity.getKilometersOnNonElectrifiedHighwayAndRegionalLines()
                );
                taxPerTrainEntityMerged.setKilometersOnNonElectrifiedLocalLines(
                        taxPerTrainEntityMerged.getKilometersOnNonElectrifiedLocalLines()
                                + taxPerTrainEntity.getKilometersOnNonElectrifiedLocalLines());
                taxPerTrainEntityMerged.setLocomotiveSeries(taxPerTrainEntity.getLocomotiveSeries());
                taxPerTrainEntityMerged.setLocomotiveWeight(taxPerTrainEntity.getLocomotiveWeight());
                taxPerTrainEntityMerged.setNotes(taxPerTrainEntity.getNotes());
                taxPerTrainEntityMerged.setId(taxPerTrainEntity.getId());
                taxPerTrainEntityMerged.setStartStation(taxPerTrainEntity.getStartStation());
                taxPerTrainEntityMerged.setEndStation(taxPerTrainEntity.getEndStation());
                taxPerTrainEntityMerged.setStrategicCoefficient(taxPerTrainEntity.getStrategicCoefficient());
                taxPerTrainEntityMerged.setTotalTrainWeight(taxPerTrainEntity.getTotalTrainWeight());
                taxPerTrainEntityMerged.setTrainLength(taxPerTrainEntity.getTrainLength());
                taxPerTrainEntityMerged.setTrainWeightWithoutLocomotive(taxPerTrainEntity.getTrainWeightWithoutLocomotive());
                taxPerTrainEntityMerged.setTrainType(taxPerTrainEntity.getTrainType());
                taxPerTrainEntityMerged.setTrainNumber(taxPerTrainEntity.getTrainNumber());
                taxPerTrainEntityMerged.setTax(taxPerTrainEntityMerged.getTax().add(taxPerTrainEntity.getTax()));

            }

            result.add(taxPerTrainEntityMerged);
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

    private boolean areSequential(TaxPerTrainHashHolder taxPerTrainHashHolder, int i) {

        return taxPerTrainHashHolder.getIndex() + 1 == i;
    }

    private TaxPerTrainHashHolder getKey(Collection<TaxPerTrainHashHolder> taxPerTrainHashHolders, TaxPerTrainHashHolder current) {

        return taxPerTrainHashHolders.stream()
                .filter(x -> x.getIndex() == current.getIndex() && Objects.equals(x.getHash(), current.getHash()))
                .findFirst().get();
    }
}