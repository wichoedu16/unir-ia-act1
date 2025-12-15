package com.romero.romero_act1.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class AuthzAndCsrfTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void unauthenticated_accessProtected_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/login")));
    }

    @Test
    void authenticated_accessProtected_ok() throws Exception {
        mockMvc.perform(get("/tasks").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void csrf_postWithoutToken_forbidden403() throws Exception {
        mockMvc.perform(post("/tasks")
                        .with(user("admin").roles("ADMIN"))
                        .param("title", "CSRF FAIL")
                        .param("description", "desc")
                        .param("priority", "MEDIUM")
                        .param("status", "TODO"))
                .andExpect(status().isForbidden());
    }

    @Test
    void csrf_postWithToken_redirects() throws Exception {
        mockMvc.perform(post("/tasks")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .param("title", "CSRF OK")
                        .param("description", "desc")
                        .param("priority", "MEDIUM")
                        .param("status", "TODO"))
                .andExpect(status().is3xxRedirection());
    }
}
