package com.example.codeassessmentexercise.helper;

import com.example.codeassessmentexercise.helper.ScreenColorTemperature;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScreenColorTemperatureTest {
    ScreenColorTemperature screenColorTemperature = new ScreenColorTemperature();

    @Test
    public void testCalculateColorTemperature_withSunrise() {
        // given
        var currentTime = LocalTime.of(6, 0);
        var sunrise = LocalTime.of(5, 30);
        var sunset = LocalTime.of(18, 0);
        // when
        var actualTemperature = screenColorTemperature.calculateColorTemperature(currentTime, sunrise, sunset);
        // then
        assertEquals(6000, actualTemperature);
    }

    @Test
    public void testCalculateColorTemperature_withMidday() {
        // given
        var currentTime = LocalTime.of(12, 0);
        var sunrise = LocalTime.of(5, 30);
        var sunset = LocalTime.of(18, 0);
        // when
        var actualTemperature = screenColorTemperature.calculateColorTemperature(currentTime, sunrise, sunset);
        // then
        assertEquals(6000, actualTemperature);
    }

    @Test
    public void testCalculateColorTemperature_withSunset() {
        // given
        var currentTime = LocalTime.of(18, 0);
        var sunrise = LocalTime.of(5, 30);
        var sunset = LocalTime.of(18, 0);
        // when
        var actualTemperature = new ScreenColorTemperature().calculateColorTemperature(currentTime, sunrise, sunset);
        // then
        assertEquals(-2000, actualTemperature);
    }

    @Test
    public void testCalculateColorTemperature_withBeforeSunrise() {
        // given
        var currentTime = LocalTime.of(4, 0);
        var sunrise = LocalTime.of(5, 30);
        var sunset = LocalTime.of(18, 0);
        // when
        var actualTemperature = screenColorTemperature.calculateColorTemperature(currentTime, sunrise, sunset);
        // then
        assertEquals(2509, actualTemperature);
    }

    @Test
    public void testCalculateColorTemperature_withAfterSunset() {
        // given
        var currentTime = LocalTime.of(20, 0);
        var sunrise = LocalTime.of(5, 30);
        var sunset = LocalTime.of(18, 0);
        // when
        var actualTemperature = screenColorTemperature.calculateColorTemperature(currentTime, sunrise, sunset);
        // then
        assertEquals(-2666, actualTemperature);
    }

}
