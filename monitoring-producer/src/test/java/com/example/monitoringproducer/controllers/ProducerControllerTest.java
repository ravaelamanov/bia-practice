package com.example.monitoringproducer.controllers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.ProducerService;
import com.example.monitoringproducer.util.AuthType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProducerController.class)
class ProducerControllerTest {
    @MockBean
    ProducerService producerService;

    @Autowired
    private MockMvc mockMvc;

    private final static String TARGET_SERVICE_HEADER = "x-target-service";
    private final static String TARGET_URI = "/123";
    private final static String SAMPLE_JWT_URL = "/jwt" + TARGET_URI;
    private final static String SAMPLE_SYNAPSE_URL = "/synapse" + TARGET_URI;
    private final static String SAMPLE_DEFAULT_URL = "/unknown" + TARGET_URI;

    @ParameterizedTest
    @MethodSource("controllerUrls")
    void whenNoTargetServiceHeader_then400(String url) throws Exception {
        doNothing().when(producerService).publish(isA(Request.class));

        mockMvc.perform(get(url))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("controllerUrls")
    void whenNoException_then200(String url) throws Exception {
        doNothing().when(producerService).publish(isA(Request.class));

        mockMvc.perform(get(url)
                        .header(TARGET_SERVICE_HEADER, "target-service"))
                .andExpect(status().isOk());

    }

    private static Stream<Arguments> controllerUrls() {
        return Stream.of(
                Arguments.of(SAMPLE_JWT_URL),
                Arguments.of(SAMPLE_SYNAPSE_URL),
                Arguments.of(SAMPLE_DEFAULT_URL)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForRequestAuthType")
    void whenRequestHandled_thenRequestAuthTypeIsExpected(String url, AuthType expectedAuthType) throws Exception {
        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);
        doNothing().when(producerService).publish(argumentCaptor.capture());

        mockMvc.perform(get(url)
                .header(TARGET_SERVICE_HEADER, "target-service"));

        Assertions.assertEquals(expectedAuthType, argumentCaptor.getValue().getAuthType());
    }

    private static Stream<Arguments> provideArgsForRequestAuthType() {
        return Stream.of(
                Arguments.of(SAMPLE_JWT_URL, AuthType.JWT),
                Arguments.of(SAMPLE_SYNAPSE_URL, AuthType.SYNAPSE),
                Arguments.of(SAMPLE_DEFAULT_URL, AuthType.DEFAULT)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForRequestTargetUri")
    void whenRequestHandled_thenRequestTargetUriIsExpected(String url, String expectedTargetUri) throws Exception {
        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);
        doNothing().when(producerService).publish(argumentCaptor.capture());

        mockMvc.perform(get(url)
                .header(TARGET_SERVICE_HEADER, "target-service"));

        Assertions.assertEquals(expectedTargetUri, argumentCaptor.getValue().getUri());
    }

    private static Stream<Arguments> provideArgsForRequestTargetUri() {
        return Stream.of(
                Arguments.of(SAMPLE_JWT_URL, TARGET_URI),
                Arguments.of(SAMPLE_SYNAPSE_URL, TARGET_URI),
                Arguments.of(SAMPLE_DEFAULT_URL, SAMPLE_DEFAULT_URL)
        );
    }

    @ParameterizedTest
    @MethodSource("controllerUrls")
    void whenPublishThrows_then200(String url) throws Exception {
        doThrow(RuntimeException.class).when(producerService).publish(isA(Request.class));

        mockMvc.perform(get(url)
                        .header(TARGET_SERVICE_HEADER, "target-service"))
                .andExpect(status().isOk());
    }

}