package ru.orlovs.handbook;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void tryLoginInvalidForm() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .content("{\"password\": \"password\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].field", is("username")));

        mockMvc.perform(post("/api/auth/login")
                .content("{\"username\": \"invalid\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].field", is("password")));
    }

    @Test
    void tryLoginWrongPassword() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .content("{\"username\": \"yuri\", \"password\": \"wrong1\"}")
                .header("Accept-Language", "en")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message", is("Bad credentials")));
    }

    @Test
    void canNotAccessAsGuest() throws Exception {
        mockMvc.perform(get("/api/regions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @RepeatedTest(3)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @WithMockUser(authorities = {"member"}, username = "yuri")
    void canAccessAsMember() throws Exception {
        mockMvc.perform(get("/api/regions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
