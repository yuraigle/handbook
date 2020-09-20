package ru.orlovs.handbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"member"}, username = "yuri")
    void shouldReturnOneRegion() throws Exception {
        this.mockMvc.perform(get("/api/regions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andExpect(jsonPath("$.capital", is("Lyon")));
    }

    @Test
    @WithMockUser(authorities = {"member"}, username = "yuri")
    void shouldValidateRequest() throws Exception {
        this.mockMvc.perform(post("/api/regions")
                .content("{\"name\":\"one\",\"area\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$[0].message", endsWith(" is required")));
    }

    @Test
    @WithMockUser(authorities = {"member"}, username = "yuri")
    void shouldCreateRegion() throws Exception {
        this.mockMvc.perform(post("/api/regions")
                .content("{\"name\":\"one\",\"capital\":\"one\",\"area\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(201));
    }
}
