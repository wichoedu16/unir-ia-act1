package com.romero.romero_act1.controller;

import com.romero.romero_act1.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskUpdateDeleteIT {

    @Autowired MockMvc mockMvc;
    @Autowired TaskRepository taskRepository;

    @BeforeEach
    void cleanDb() {
        taskRepository.deleteAll();
    }

    private static Long extractIdFromRedirect(String url) {
        if (url == null) return null;
        String[] parts = url.split("/");
        if (parts.length == 0) return null;
        String last = parts[parts.length - 1];
        if (!last.matches("\\d+")) return null;
        return Long.parseLong(last);
    }

    @Test
    void update_then_delete_shouldReflectInList() throws Exception {
        // Arrange: create
        String original = "Task Original";
        MvcResult created = mockMvc.perform(post("/tasks")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .param("title", original)
                        .param("description", "desc")
                        .param("priority", "LOW")
                        .param("status", "TODO"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirected = created.getResponse().getRedirectedUrl();
        assertNotNull(redirected);
        Long id = extractIdFromRedirect(redirected);
        assertNotNull(id);

        // Act: update
        String updated = "Task Updated";
        mockMvc.perform(post("/tasks/" + id)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .param("title", updated)
                        .param("description", "desc2")
                        .param("priority", "HIGH")
                        .param("status", "DOING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/tasks/" + id)));

        // Assert: list contains updated
        mockMvc.perform(get("/tasks").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(updated)));

        // Act: delete
        mockMvc.perform(post("/tasks/" + id + "/delete")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/tasks")));

        // Assert: list not contains updated
        mockMvc.perform(get("/tasks").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString(updated))));
    }
}
