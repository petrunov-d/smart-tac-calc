package com.dp.trains.services;

import com.dp.trains.common.ServiceRegistry;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataCopyingService {

    private final ServiceRegistry serviceRegistry;

    public Map<BaseImportService, Integer> getDataForYear(int year) {

        return serviceRegistry.getBaseImportServiceList()
                .parallelStream()
                .collect(Collectors.toMap(x -> x, x -> x.countByYear(year)));
    }

    public boolean hasAllData(Map<BaseImportService, Integer> dataForCurrentYear) {

        return dataForCurrentYear.entrySet()
                .stream()
                .allMatch(x -> x.getValue() > 0);
    }

    public boolean isCompletelyEmpty(Map<BaseImportService, Integer> data) {

        return data.entrySet()
                .stream()
                .allMatch(x -> x.getValue() == 0);
    }

    public List<BaseImportService> merge(Map<BaseImportService, Integer> dataForLastYear,
                                         Map<BaseImportService, Integer> dataForCurrentYear) {

        Map<BaseImportService, Integer> nonEmptyDataForLastYear = getNonEmpty(dataForLastYear);
        Map<BaseImportService, Integer> emptyDataForCurrentYear = getEmpty(dataForCurrentYear);

        Set<BaseImportService> intersection = Sets.newHashSet();

        emptyDataForCurrentYear.keySet().forEach(x -> {

            for (BaseImportService baseImportService : nonEmptyDataForLastYear.keySet()) {

                if (x.getDisplayName().equals(baseImportService.getDisplayName())) {
                    intersection.add(x);
                    break;
                }
            }
        });

        log.info("Non empty data from last year is: " + Joiner.on(",").withKeyValueSeparator("=")
                .join(nonEmptyDataForLastYear) + " .Empty data for current year is: " +
                Joiner.on(",").withKeyValueSeparator("=").join(emptyDataForCurrentYear)
                + " .Intersection of the above maps can be applied for services: " + Joiner.on(",")
                .join(intersection.stream().map(BaseImportService::getDisplayName).collect(Collectors.toList())));

        return Lists.newArrayList(intersection);
    }

    private Map<BaseImportService, Integer> getNonEmpty(Map<BaseImportService, Integer> map) {

        return map.entrySet()
                .stream()
                .filter(x -> x.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<BaseImportService, Integer> getEmpty(Map<BaseImportService, Integer> map) {

        return map.entrySet()
                .stream()
                .filter(x -> x.getValue() == 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}