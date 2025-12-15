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
class IncidentControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void create_then_list_contains_record() throws Exception {
        String summary = "Incident IT - Latencia alta";

        mockMvc.perform(post("/incidents")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .param("summary", summary)
                        .param("serviceName", "payments")
                        .param("severity", "HIGH")
                        .param("status", "OPEN")
                        .param("timelineNotes", "Detectado por monitor\nMitigación aplicada"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/incidents").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(summary)));
    }
}
