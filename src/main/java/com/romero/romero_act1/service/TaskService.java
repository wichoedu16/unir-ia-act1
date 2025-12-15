package com.romero.romero_act1.service;

import com.romero.romero_act1.model.Task;
import com.romero.romero_act1.model.TaskStatus;
import com.romero.romero_act1.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> findAll() {
        return repo.findAll();
    }

    public Task getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Task no existe (id=" + id + ")"));
    }

    public Task create(Task t) {
        t.setId(null);
        return repo.save(t);
    }

    public Task update(Long id, Task form) {
        Task existing = getOrThrow(id);
        existing.setTitle(form.getTitle());
        existing.setDescription(form.getDescription());
        existing.setPriority(form.getPriority());
        existing.setStatus(form.getStatus());
        existing.setDueDate(form.getDueDate());
        existing.setTags(form.getTags());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Task existing = getOrThrow(id);
        repo.delete(existing);
    }

    public long countByStatus(TaskStatus status) {
        return repo.countByStatus(status);
    }

    public long countOverdue(LocalDate today) {
        return repo.countByDueDateBeforeAndStatusNot(today, TaskStatus.DONE);
    }
}
