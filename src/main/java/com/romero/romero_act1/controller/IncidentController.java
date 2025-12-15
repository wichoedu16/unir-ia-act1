package com.romero.romero_act1.controller;

import com.romero.romero_act1.model.Incident;
import com.romero.romero_act1.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService service;

    public IncidentController(IncidentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("incidents", service.findAll());
        return "incidents/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("incident", new Incident());
        model.addAttribute("actionUrl", "/incidents");
        model.addAttribute("submitLabel", "Crear");
        return "incidents/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("incident") Incident incident, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/incidents");
            model.addAttribute("submitLabel", "Crear");
            return "incidents/form";
        }
        Incident saved = service.create(incident);
        return "redirect:/incidents/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("incident", service.getOrThrow(id));
        return "incidents/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("incident", service.getOrThrow(id));
        model.addAttribute("actionUrl", "/incidents/" + id);
        model.addAttribute("submitLabel", "Actualizar");
        return "incidents/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("incident") Incident incident, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/incidents/" + id);
            model.addAttribute("submitLabel", "Actualizar");
            return "incidents/form";
        }
        service.update(id, incident);
        return "redirect:/incidents/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/incidents";
    }
}
