package com.dp.trains.model.entities;

import com.dp.trains.model.entities.listeners.YearDiscriminatingEntityListener;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Data
@FilterDef(name = YearDiscriminatingEntity.YEAR_FILTER,
        parameters = @ParamDef(name = YearDiscriminatingEntity.YEAR, type = "int"))
@Filters(@Filter(name = YearDiscriminatingEntity.YEAR_FILTER,
        condition = YearDiscriminatingEntity.YEAR + " = :" + YearDiscriminatingEntity.YEAR)
)
@MappedSuperclass
@EntityListeners(YearDiscriminatingEntityListener.class)
public class YearDiscriminatingEntity {

    public static final String YEAR = "year";
    public static final String YEAR_FILTER = "year_filter";

    @Column(name = YEAR, columnDefinition = "INTEGER")
    private Integer year;

    @Transient
    private Boolean shouldUpdateYear = Boolean.TRUE;
}
