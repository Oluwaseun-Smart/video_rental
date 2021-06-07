package com.oos.rental;


import com.oos.rental.controllers.IndexController;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController)
                .build();
    }

    @Test
    public void testAppIndexEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/index")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is(true)))
                .andExpect(jsonPath("$.code", Matchers.is(100)))
                .andExpect(jsonPath("$.message", Matchers.is("WELCOME TO VIDEO RENTAL APP")));

    }
}