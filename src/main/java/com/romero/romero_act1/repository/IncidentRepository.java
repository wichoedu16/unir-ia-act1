package com.romero.romero_act1.repository;

import com.romero.romero_act1.model.Incident;
import com.romero.romero_act1.model.IncidentSeverity;
import com.romero.romero_act1.model.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    long countByStatusAndSeverity(IncidentStatus status, IncidentSeverity severity);
}
