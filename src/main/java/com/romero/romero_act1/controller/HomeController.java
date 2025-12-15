package com.romero.romero_act1.controller;

import com.romero.romero_act1.model.IncidentSeverity;
import com.romero.romero_act1.model.TaskStatus;
import com.romero.romero_act1.service.IncidentService;
import com.romero.romero_act1.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final TaskService taskService;
    private final IncidentService incidentService;

    public HomeController(TaskService taskService, IncidentService incidentService) {
        this.taskService = taskService;
        this.incidentService = incidentService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("todoCount", taskService.countByStatus(TaskStatus.TODO));
        model.addAttribute("doingCount", taskService.countByStatus(TaskStatus.DOING));
        model.addAttribute("doneCount", taskService.countByStatus(TaskStatus.DONE));
        model.addAttribute("overdueCount", taskService.countOverdue(LocalDate.now()));

        model.addAttribute("openLow", incidentService.countOpenBySeverity(IncidentSeverity.LOW));
        model.addAttribute("openMedium", incidentService.countOpenBySeverity(IncidentSeverity.MEDIUM));
        model.addAttribute("openHigh", incidentService.countOpenBySeverity(IncidentSeverity.HIGH));
        model.addAttribute("openCritical", incidentService.countOpenBySeverity(IncidentSeverity.CRITICAL));

        return "dashboard";
    }
}
