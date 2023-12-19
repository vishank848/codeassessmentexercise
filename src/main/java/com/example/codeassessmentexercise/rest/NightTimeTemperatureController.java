package com.example.codeassessmentexercise.rest;


import com.example.codeassessmentexercise.response.NightTimeTemperatureResponse;
import com.example.codeassessmentexercise.service.NightTimeTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NightTimeTemperatureController {
    private final NightTimeTemperatureService nightTimeTemperatureService;

    @Autowired
    public NightTimeTemperatureController(NightTimeTemperatureService nightTimeTemperatureService) {
        this.nightTimeTemperatureService = nightTimeTemperatureService;
    }

    @GetMapping(value = "/night-time-temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    public NightTimeTemperatureResponse getTemperature(@RequestParam("lat") String latitude,
                                                       @RequestParam("lng") String longitude,
                                                       @RequestParam(name="date", required = false) String date,
                                                       @RequestParam(name="tzId", required = false) String timeZoneId) {
        return nightTimeTemperatureService.getTemperature(latitude, longitude, date, timeZoneId);
    }
}
