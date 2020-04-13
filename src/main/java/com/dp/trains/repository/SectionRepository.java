package com.dp.trains.repository;

import com.dp.trains.model.entities.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {

    Set<SectionEntity> findAllByFirstKeyPoint(String firstKeyPoint);

    int countByYear(int year);

    List<SectionEntity> findAllByYear(Integer previousYear);

    SectionEntity findByFirstKeyPointAndLineNumber(String firstKeyPoint, Integer lineNumber);

    @Query(value = "select s.* from section s inner join sub_section ss on s.id =ss.section_fk where ss.non_key_station = :nonKeyStation",
            nativeQuery = true)
    SectionEntity findBySubsectionEntity(@Param("nonKeyStation") String nonKeyStation);
}
