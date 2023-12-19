package com.example.codeassessmentexercise.config;

import com.example.codeassessmentexercise.client.SunApiClient;
import com.example.codeassessmentexercise.connector.HttpConnector;
import com.example.codeassessmentexercise.service.NightTimeTemperatureService;
import com.example.codeassessmentexercise.helper.ScreenColorTemperature;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfig {
    @Value("${api.sunrise.sunset.host}")
    private String baseApiHost;
    @Value("${api.sunrise.sunset.uri}")
    private String uriPath;

    @Bean
    public HttpConnector httpConnector(WebClient webClient) {
        return new HttpConnector(webClient);
    }

    @Bean
    public SunApiClient sunApiClient(HttpConnector httpConnector) {
        return new SunApiClient(httpConnector);
    }

    @Bean
    public NightTimeTemperatureService nightTimeTemperatureService(SunApiClient sunApiClient,
                                                                   ScreenColorTemperature screenColorTemperature) {
        return new NightTimeTemperatureService(sunApiClient, uriPath, screenColorTemperature);
    }

    @Bean
    public ScreenColorTemperature screenColorTemperature() {
        return new ScreenColorTemperature();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseApiHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
