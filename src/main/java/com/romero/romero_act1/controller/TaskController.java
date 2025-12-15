package com.romero.romero_act1.controller;

import com.romero.romero_act1.model.Task;
import com.romero.romero_act1.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("actionUrl", "/tasks");
        model.addAttribute("submitLabel", "Crear");
        return "tasks/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("task") Task task, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/tasks");
            model.addAttribute("submitLabel", "Crear");
            return "tasks/form";
        }
        Task saved = service.create(task);
        return "redirect:/tasks/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("task", service.getOrThrow(id));
        return "tasks/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", service.getOrThrow(id));
        model.addAttribute("actionUrl", "/tasks/" + id);
        model.addAttribute("submitLabel", "Actualizar");
        return "tasks/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("task") Task task, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/tasks/" + id);
            model.addAttribute("submitLabel", "Actualizar");
            return "tasks/form";
        }
        service.update(id, task);
        return "redirect:/tasks/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/tasks";
    }
}
