package com.romero.romero_act1.validation;

import com.romero.romero_act1.model.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static boolean hasViolation(Set<? extends ConstraintViolation<?>> violations, String field) {
        return violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(field));
    }

    @Test
    void task_blankTitle_shouldViolate() {
        Task t = new Task();
        t.setTitle(""); // invalid
        t.setPriority(TaskPriority.MEDIUM);
        t.setStatus(TaskStatus.TODO);

        Set<ConstraintViolation<Task>> violations = validator.validate(t);

        assertFalse(violations.isEmpty());
        assertTrue(hasViolation(violations, "title"));
    }

    @Test
    void runbook_missingRequired_shouldViolate() {
        Runbook r = new Runbook();
        r.setName("");        // invalid
        r.setServiceName(""); // invalid
        r.setSteps("");       // invalid

        Set<ConstraintViolation<Runbook>> violations = validator.validate(r);

        assertFalse(violations.isEmpty());
        assertTrue(hasViolation(violations, "name"));
        assertTrue(hasViolation(violations, "serviceName"));
        assertTrue(hasViolation(violations, "steps"));
    }

    @Test
    void incident_blankSummary_shouldViolate() {
        Incident i = new Incident();
        i.setSummary(""); // invalid
        i.setServiceName("svc");
        i.setSeverity(IncidentSeverity.LOW);
        i.setStatus(IncidentStatus.OPEN);

        Set<ConstraintViolation<Incident>> violations = validator.validate(i);

        assertFalse(violations.isEmpty());
        assertTrue(hasViolation(violations, "summary"));
    }
}
