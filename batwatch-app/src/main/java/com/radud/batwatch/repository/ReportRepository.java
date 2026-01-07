package com.radud.batwatch.repository;

import com.radud.batwatch.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT distinct report.* FROM report " +
            "JOIN location on report.id = location.report_id " +
            "WHERE ST_DWithin(location.location_point::geography, " +
            "ST_SetSRID(ST_MakePoint(?1, ?2), 4326)::geography, ?3)",
            nativeQuery = true)
    List<Report> findByCityId(Double longitude, Double latitude, Long radiusInMeters);
}
