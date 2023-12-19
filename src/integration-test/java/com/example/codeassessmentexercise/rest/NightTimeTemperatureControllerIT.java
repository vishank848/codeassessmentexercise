package com.example.codeassessmentexercise.rest;

import com.example.codeassessmentexercise.NightTimeTemperatureApplication;
import com.example.codeassessmentexercise.service.NightTimeTemperatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = NightTimeTemperatureApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class NightTimeTemperatureControllerIT {

    @Autowired
    private NightTimeTemperatureService nightTimeTemperatureService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTemperature_withAllParameters() throws Exception {
        String latitude = "43.66258321585993";
        String longitude = "-79.39152689466948";
        String date = "2023-12-20";
        String timeZoneId = "UTC";
        mockMvc.perform(get("/night-time-temperature")
                        .param("lat", latitude)
                        .param("lng", longitude)
                        .param("date", date)
                        .param("tzId", timeZoneId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", equalTo(6000)));
    }

    @Test
    public void testGetTemperature_withMissingParams() throws Exception {
        String latitude = "43.66258321585993";
        String longitude = "-79.39152689466948";
        mockMvc.perform(get("/night-time-temperature")
                        .param("lat", latitude)
                        .param("lng", longitude)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", equalTo(6000)));
    }
}