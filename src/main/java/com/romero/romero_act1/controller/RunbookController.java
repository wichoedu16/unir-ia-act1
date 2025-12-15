package com.romero.romero_act1.controller;

import com.romero.romero_act1.model.Runbook;
import com.romero.romero_act1.service.RunbookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/runbooks")
public class RunbookController {

    private final RunbookService service;

    public RunbookController(RunbookService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("runbooks", service.findAll());
        return "runbooks/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("runbook", new Runbook());
        model.addAttribute("actionUrl", "/runbooks");
        model.addAttribute("submitLabel", "Crear");
        return "runbooks/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("runbook") Runbook runbook, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/runbooks");
            model.addAttribute("submitLabel", "Crear");
            return "runbooks/form";
        }
        Runbook saved = service.create(runbook);
        return "redirect:/runbooks/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("runbook", service.getOrThrow(id));
        return "runbooks/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("runbook", service.getOrThrow(id));
        model.addAttribute("actionUrl", "/runbooks/" + id);
        model.addAttribute("submitLabel", "Actualizar");
        return "runbooks/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("runbook") Runbook runbook, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/runbooks/" + id);
            model.addAttribute("submitLabel", "Actualizar");
            return "runbooks/form";
        }
        service.update(id, runbook);
        return "redirect:/runbooks/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/runbooks";
    }
}
