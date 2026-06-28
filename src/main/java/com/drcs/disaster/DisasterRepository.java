package com.drcs.disaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster, UUID> {

    List<Disaster> findByStatus(DisasterStatus status);

    List<Disaster> findBySeverityLevel(SeverityLevel severityLevel);
}