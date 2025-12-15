package com.romero.romero_act1.repository;

import com.romero.romero_act1.model.Task;
import com.romero.romero_act1.model.TaskPriority;
import com.romero.romero_act1.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    TaskRepository repo;

    @Test
    void overdueQueryAndCountsByStatus_workAsExpected() {
        // Arrange
        LocalDate today = LocalDate.now();

        Task t1 = new Task();
        t1.setTitle("Overdue TODO");
        t1.setPriority(TaskPriority.MEDIUM);
        t1.setStatus(TaskStatus.TODO);
        t1.setDueDate(today.minusDays(1));

        Task t2 = new Task();
        t2.setTitle("Future DOING");
        t2.setPriority(TaskPriority.LOW);
        t2.setStatus(TaskStatus.DOING);
        t2.setDueDate(today.plusDays(2));

        Task t3 = new Task();
        t3.setTitle("Overdue DONE");
        t3.setPriority(TaskPriority.HIGH);
        t3.setStatus(TaskStatus.DONE);
        t3.setDueDate(today.minusDays(2));

        repo.save(t1);
        repo.save(t2);
        repo.save(t3);

        // Act
        long todoCount = repo.countByStatus(TaskStatus.TODO);
        long doingCount = repo.countByStatus(TaskStatus.DOING);
        long doneCount = repo.countByStatus(TaskStatus.DONE);

        long overdueCount = repo.countByDueDateBeforeAndStatusNot(today, TaskStatus.DONE);
        var overdueList = repo.findByDueDateBeforeAndStatusNot(today, TaskStatus.DONE);

        // Assert
        assertEquals(1, todoCount);
        assertEquals(1, doingCount);
        assertEquals(1, doneCount);

        assertEquals(1, overdueCount);
        assertEquals(1, overdueList.size());
        assertEquals("Overdue TODO", overdueList.get(0).getTitle());
    }
}
