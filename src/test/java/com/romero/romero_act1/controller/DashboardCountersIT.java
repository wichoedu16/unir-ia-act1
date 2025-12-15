package com.romero.romero_act1.controller;

import com.romero.romero_act1.model.*;
import com.romero.romero_act1.repository.IncidentRepository;
import com.romero.romero_act1.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DashboardCountersIT {

    @Autowired MockMvc mockMvc;
    @Autowired TaskRepository taskRepository;
    @Autowired IncidentRepository incidentRepository;

    @BeforeEach
    void seed() {
        incidentRepository.deleteAll();
        taskRepository.deleteAll();

        Task overdueTodo = new Task();
        overdueTodo.setTitle("Overdue TODO");
        overdueTodo.setPriority(TaskPriority.MEDIUM);
        overdueTodo.setStatus(TaskStatus.TODO);
        overdueTodo.setDueDate(LocalDate.now().minusDays(1));
        taskRepository.save(overdueTodo);

        Task done = new Task();
        done.setTitle("Done");
        done.setPriority(TaskPriority.LOW);
        done.setStatus(TaskStatus.DONE);
        done.setDueDate(LocalDate.now().minusDays(3));
        taskRepository.save(done);

        Incident openLow = new Incident();
        openLow.setSummary("Open LOW");
        openLow.setServiceName("svc");
        openLow.setSeverity(IncidentSeverity.LOW);
        openLow.setStatus(IncidentStatus.OPEN);
        incidentRepository.save(openLow);

        Incident openHigh = new Incident();
        openHigh.setSummary("Open HIGH");
        openHigh.setServiceName("svc");
        openHigh.setSeverity(IncidentSeverity.HIGH);
        openHigh.setStatus(IncidentStatus.OPEN);
        incidentRepository.save(openHigh);

        Incident closedCritical = new Incident();
        closedCritical.setSummary("Closed CRITICAL");
        closedCritical.setServiceName("svc");
        closedCritical.setSeverity(IncidentSeverity.CRITICAL);
        closedCritical.setStatus(IncidentStatus.CLOSED);
        incidentRepository.save(closedCritical);
    }

    @Test
    void dashboardCounters_matchSeededData() throws Exception {
        mockMvc.perform(get("/").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("todoCount", anyOf(is(1), is(1L))))
                .andExpect(model().attribute("doingCount", anyOf(is(0), is(0L))))
                .andExpect(model().attribute("doneCount", anyOf(is(1), is(1L))))
                .andExpect(model().attribute("overdueCount", anyOf(is(1), is(1L))))
                .andExpect(model().attribute("openLow", anyOf(is(1), is(1L))))
                .andExpect(model().attribute("openMedium", anyOf(is(0), is(0L))))
                .andExpect(model().attribute("openHigh", anyOf(is(1), is(1L))))
                .andExpect(model().attribute("openCritical", anyOf(is(0), is(0L))));
    }
}
