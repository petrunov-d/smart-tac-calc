package com.dp.trains.utils.mapper.impl;

import com.googlecode.jmapper.api.enums.MappingType;

/**
 * Default enrichment service that maps only valued fields.
 *
 * @param <D> type of DTOs to use for mapping
 * @param <E> type of Entities to use for mapping
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultDtoEntityEnrichmentService<D, E> extends FilteredDtoEntityEnrichmentService<D, E> {

    public DefaultDtoEntityEnrichmentService(final Class<D> dtoClass, final Class<E> entityClass) {
        super(dtoClass, entityClass, MappingType.ALL_FIELDS, MappingType.ALL_FIELDS, MappingType.ONLY_VALUED_FIELDS);
    }

    public static DefaultDtoEntityEnrichmentService create(final Class dtoClass, final Class entityClass) {

        return new DefaultDtoEntityEnrichmentService(dtoClass, entityClass);
    }
}
