package com.example.codeassessmentexercise.client;

import com.example.codeassessmentexercise.connector.HttpConnector;
import com.example.codeassessmentexercise.request.NightTimeTemperatureRequest;
import com.google.common.base.Joiner;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SunApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SunApiClient.class);
    private final HttpConnector httpConnector;

    public SunApiClient(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    public NightTimeTemperatureRequest getRequest(String endpoint, Map<String, String> queryParam) {
        LOGGER.info("api called with data {}", queryParam);
        return new GsonBuilder().create()
                .fromJson(httpConnector.getRequest(prepareEndpoint(endpoint, queryParam)),
                        NightTimeTemperatureRequest.class);
    }

    private String prepareEndpoint(String endpoint, Map<String, String> queryParam) {
        return endpoint + "?" + Joiner.on("&").withKeyValueSeparator("=").join(queryParam);
    }

}
