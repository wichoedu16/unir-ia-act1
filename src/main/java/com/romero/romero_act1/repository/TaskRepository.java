package com.romero.romero_act1.repository;

import com.romero.romero_act1.model.Task;
import com.romero.romero_act1.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(TaskStatus status);

    long countByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);
}
