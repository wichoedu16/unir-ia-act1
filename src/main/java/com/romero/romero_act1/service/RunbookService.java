package com.romero.romero_act1.service;

import com.romero.romero_act1.model.Runbook;
import com.romero.romero_act1.repository.RunbookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunbookService {

    private final RunbookRepository repo;

    public RunbookService(RunbookRepository repo) {
        this.repo = repo;
    }

    public List<Runbook> findAll() {
        return repo.findAll();
    }

    public Runbook getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Runbook no existe (id=" + id + ")"));
    }

    public Runbook create(Runbook r) {
        r.setId(null);
        return repo.save(r);
    }

    public Runbook update(Long id, Runbook form) {
        Runbook existing = getOrThrow(id);
        existing.setName(form.getName());
        existing.setServiceName(form.getServiceName());
        existing.setSteps(form.getSteps());
        existing.setCommands(form.getCommands());
        existing.setLastReviewedAt(form.getLastReviewedAt());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Runbook existing = getOrThrow(id);
        repo.delete(existing);
    }
}
