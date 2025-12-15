package com.romero.romero_act1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void create_redirect_then_list_contains_record() throws Exception {
        String title = "Task IT - Crear";

        mockMvc.perform(post("/tasks")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .param("title", title)
                        .param("description", "desc")
                        .param("priority", "MEDIUM")
                        .param("status", "TODO")
                        .param("tags", "it,test"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/tasks").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }
}
