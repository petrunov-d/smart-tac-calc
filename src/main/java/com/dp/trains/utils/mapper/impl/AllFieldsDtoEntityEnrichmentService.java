package com.dp.trains.utils.mapper.impl;

import com.googlecode.jmapper.api.enums.MappingType;

/**
 * Enrichment service which maps all fields including the ones with null value.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AllFieldsDtoEntityEnrichmentService<D, E> extends FilteredDtoEntityEnrichmentService<D, E> {

    public AllFieldsDtoEntityEnrichmentService(final Class<D> dtoClass, final Class<E> entityClass) {
        super(dtoClass, entityClass, MappingType.ALL_FIELDS, MappingType.ALL_FIELDS, MappingType.ALL_FIELDS);
    }

    public static AllFieldsDtoEntityEnrichmentService create(final Class dtoClass, final Class entityClass) {

        return new AllFieldsDtoEntityEnrichmentService(dtoClass, entityClass);
    }
}