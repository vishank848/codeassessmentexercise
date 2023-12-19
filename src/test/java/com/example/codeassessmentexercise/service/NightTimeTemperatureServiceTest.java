package com.example.codeassessmentexercise.service;

import com.example.codeassessmentexercise.client.SunApiClient;
import com.example.codeassessmentexercise.helper.ScreenColorTemperature;
import com.example.codeassessmentexercise.request.NightTimeTemperatureRequest;
import com.example.codeassessmentexercise.request.Results;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class NightTimeTemperatureServiceTest {
    private final String uriPath = "/path";
    private final SunApiClient sunApiClient = mock(SunApiClient.class);
    private final ScreenColorTemperature screenColorTemperature = mock(ScreenColorTemperature.class);
    private final NightTimeTemperatureService nightTimeTemperatureService =
            new NightTimeTemperatureService(sunApiClient, uriPath, screenColorTemperature);

    @Test
    void testGetTemperature_whenAllParametersPresent() {
        // given
        String latitude = "12.34";
        String longitude = "56.78";
        String date = "2022-01-01";
        String timeZoneId = "UTC";
        int expectedTemperature = 2500;
        when(sunApiClient.getRequest(uriPath, Map.of("lat", latitude, "lng", longitude,
                "date", date, "tzId", timeZoneId))).thenReturn(prepareRequest());
        when(screenColorTemperature.calculateColorTemperature(any(LocalTime.class), any(LocalTime.class),
                any(LocalTime.class))).thenReturn(expectedTemperature);
        // when
        var actualResponse = nightTimeTemperatureService.getTemperature(latitude, longitude, date, timeZoneId);
        // then
        assertEquals(expectedTemperature, actualResponse.getTemperature());
    }

    @Test
    void testGetTemperature_whenSomeParametersAreMissing() {
        // given
        String latitude = "40.7128";
        String longitude = "-74.0060";
        int expectedTemperature = 6000;
        when(sunApiClient.getRequest(anyString(), anyMap())).thenReturn(prepareRequest());
        when(screenColorTemperature.calculateColorTemperature(any(LocalTime.class), any(LocalTime.class),
                any(LocalTime.class))).thenReturn(expectedTemperature);
        // when
        var response = nightTimeTemperatureService.getTemperature(latitude, longitude, "null","null");
        // then
        assertEquals(expectedTemperature, response.getTemperature());
    }

    private NightTimeTemperatureRequest prepareRequest() {
        NightTimeTemperatureRequest nightTimeTemperatureRequest = new NightTimeTemperatureRequest();
        Results results = new Results();
        results.setSunrise("7:27:02 AM");
        results.setSunset("5:05:55 PM");
        results.setSolar_noon("12:16:28 PM");
        results.setDay_length("9:38:53");
        results.setCivil_twilight_begin("6:58:14 AM");
        results.setCivil_twilight_end("5:34:43 PM");
        results.setNautical_twilight_begin("6:25:47 AM");
        results.setNautical_twilight_end("6:07:10 PM");
        results.setAstronomical_twilight_begin("5:54:14 AM");
        results.setAstronomical_twilight_end("6:38:43 PM");
        nightTimeTemperatureRequest.setResults(results);
        nightTimeTemperatureRequest.setStatus("OK");
        nightTimeTemperatureRequest.setTzId("UTC");
        return nightTimeTemperatureRequest;
    }
    
}
