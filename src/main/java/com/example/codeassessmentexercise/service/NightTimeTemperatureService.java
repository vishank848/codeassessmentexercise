package com.example.codeassessmentexercise.service;


import com.example.codeassessmentexercise.client.SunApiClient;
import com.example.codeassessmentexercise.helper.ScreenColorTemperature;
import com.example.codeassessmentexercise.request.NightTimeTemperatureRequest;
import com.example.codeassessmentexercise.response.NightTimeTemperatureResponse;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class NightTimeTemperatureService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NightTimeTemperatureService.class);
    private final String uriPath;
    private final SunApiClient sunApiClient;
    private final ScreenColorTemperature screenColorTemperature;

    public NightTimeTemperatureService(SunApiClient sunApiClient, String uriPath,
                                       ScreenColorTemperature screenColorTemperature) {
        this.sunApiClient = sunApiClient;
        this.uriPath = uriPath;
        this.screenColorTemperature = screenColorTemperature;
    }

    public NightTimeTemperatureResponse getTemperature(String latitude, String longitude, String date,
                                                       String timeZoneId) {
        NightTimeTemperatureResponse nightTimeTemperatureResponse = new NightTimeTemperatureResponse();
        NightTimeTemperatureRequest request = sunApiClient
                .getRequest(uriPath, prepareMap(latitude, longitude, date, timeZoneId));
        LOGGER.info("response from sunrise sunset api : {}", request);
        int temperature = screenColorTemperature.calculateColorTemperature(
                findLocalTimeOfZone(timeZoneId), parseDateTime(request.getResults().getSunrise()),
                parseDateTime(request.getResults().getSunset()));
        LOGGER.info("color temperature in Kelvin : {}", temperature);
        nightTimeTemperatureResponse.setTemperature(temperature);
        return nightTimeTemperatureResponse;
    }

    private LocalTime findLocalTimeOfZone(String timeZoneId) {
        TimeZone timeZone = StringUtils.isNotBlank(timeZoneId) 
                ? TimeZone.getTimeZone(timeZoneId) 
                : TimeZone.getTimeZone("UTC");
        return LocalDateTime.now(timeZone.toZoneId()).toLocalTime();
    }

    private Map<String, String> prepareMap(String latitude, String longitude, String date, String timeZoneId) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("lat", latitude);
        queryParam.put("lng", longitude);
        queryParam.put("date", StringUtils.isNotBlank(date) ? date : "today");
        queryParam.put("tzId", StringUtils.isNotBlank(timeZoneId) ? timeZoneId : "UTC");
        return queryParam;
    }

    private LocalTime parseDateTime(String value) {
        return LocalTime.parse(value, DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US));
    }
}
