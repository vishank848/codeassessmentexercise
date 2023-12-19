package com.example.codeassessmentexercise.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class ScreenColorTemperature {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenColorTemperature.class);

    public int calculateColorTemperature(LocalTime currentTime, LocalTime sunrise, LocalTime sunset) {
        LOGGER.info("calculation done on data currentTime : {}, sunrise : {}, sunset : {}",
                currentTime, sunrise, sunset);
        int sunriseTemperature = 2700;
        int middayTemperature = 6000;
        int sunsetTemperature = 4000;
        if (currentTime.isBefore(sunrise)) {
            return interpolateColorTemperature
                    (currentTime, LocalTime.MIN, sunrise, 2000, sunriseTemperature);
        } else if (currentTime.isBefore(sunset)) {
            return middayTemperature;
        } else {
            return interpolateColorTemperature
                    (currentTime, sunset, LocalTime.MAX, sunsetTemperature, 2000);
        }
    }

    private static int interpolateColorTemperature(LocalTime currentTime, LocalTime sunriseOrSet_v1,
                                                   LocalTime sunriseOrSet_v2, int sunriseTemperature_v1,
                                                   int sunriseTemperature_v2) {
        double alpha = (double) currentTime.toSecondOfDay()
                / (double) (sunriseOrSet_v2.toSecondOfDay() - sunriseOrSet_v1.toSecondOfDay());
        return (int) (sunriseTemperature_v1 + alpha * (sunriseTemperature_v2 - sunriseTemperature_v1));
    }
}
