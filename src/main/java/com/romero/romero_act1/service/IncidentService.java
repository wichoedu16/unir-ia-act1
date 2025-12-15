package com.romero.romero_act1.service;

import com.romero.romero_act1.model.Incident;
import com.romero.romero_act1.model.IncidentSeverity;
import com.romero.romero_act1.model.IncidentStatus;
import com.romero.romero_act1.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository repo;

    public IncidentService(IncidentRepository repo) {
        this.repo = repo;
    }

    public List<Incident> findAll() {
        return repo.findAll();
    }

    public Incident getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Incident no existe (id=" + id + ")"));
    }

    public Incident create(Incident i) {
        i.setId(null);
        return repo.save(i);
    }

    public Incident update(Long id, Incident form) {
        Incident existing = getOrThrow(id);
        existing.setSummary(form.getSummary());
        existing.setServiceName(form.getServiceName());
        existing.setSeverity(form.getSeverity());
        existing.setStatus(form.getStatus());
        existing.setTimelineNotes(form.getTimelineNotes());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Incident existing = getOrThrow(id);
        repo.delete(existing);
    }

    public long countOpenBySeverity(IncidentSeverity severity) {
        return repo.countByStatusAndSeverity(IncidentStatus.OPEN, severity);
    }
}
