package com.example.codeassessmentexercise.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NightTimeTemperatureRequest {
    private Results results;
    private String status;
    private String tzId;
}
