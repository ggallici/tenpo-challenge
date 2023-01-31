package com.ggallici.tenpo.services.wrappers;

import com.ggallici.tenpo.exceptions.RestServiceException;
import com.ggallici.tenpo.properties.RestProperties;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceTest {
    private final WireMockServer wireMockServer = new WireMockServer();

    @Mock
    private RestProperties restPropertiesMock;

    private RestService restService;

    @Before
    public void setUp() {
        this.restService = new RestService(restPropertiesMock);
        wireMockServer.start();
    }

    @After
    public void tearDown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    public void testGet_1() {
        doTestCase(1, false, okResponse(), okResponse(), okResponse());
    }

    @Test
    public void testGet_2() {
        doTestCase(2, false, errorResponse(), okResponse(), okResponse());
    }

    @Test
    public void testGet_3() {
        doTestCase(3, false, errorResponse(), errorResponse(), okResponse());
    }

    @Test
    public void testGet_4() {
        doTestCase(3, true, errorResponse(), errorResponse(), errorResponse());
    }

    private void doTestCase(int expectedAttempts, boolean exceptionExpected, ResponseDefinitionBuilder... responses) {
        var service = "test_service";
        var host = "http://localhost:8080";
        var resource = "/test";

        var maxRetries = 2L;

        var secondRequest = "Second request";
        var thirdRequest = "Third request";

        wireMockServer.stubFor(get(resource)
                .inScenario("GET")
                .whenScenarioStateIs(STARTED)
                .willReturn(responses[0])
                .willSetStateTo(secondRequest));

        wireMockServer.stubFor(get(resource)
                .inScenario("GET")
                .whenScenarioStateIs(secondRequest)
                .willReturn(responses[1])
                .willSetStateTo(thirdRequest));

        wireMockServer.stubFor(get(resource)
                .inScenario("GET")
                .whenScenarioStateIs(thirdRequest)
                .willReturn(responses[2]));

        doReturn(maxRetries).when(restPropertiesMock).getRetries(service);

        if (exceptionExpected) {
            assertThatThrownBy(() -> doGet(service, host, resource))
                    .isInstanceOf(RestServiceException.class)
                    .hasMessage("External service error - Retries exhausted after 3 attempts")
                    .hasRootCauseMessage("400 Bad Request from GET http://localhost:8080/test");
        } else {
            assertThat(doGet(service, host, resource))
                    .isEqualTo(new TestDto("Ok"));
        }

        wireMockServer.verify(expectedAttempts, getRequestedFor(urlEqualTo(resource)));
        verify(restPropertiesMock, atLeastOnce()).getRetries(service);
    }

    private TestDto doGet(String service, String host, String resource) {
        return restService.getObject(service, host + resource, TestDto.class);
    }

    private ResponseDefinitionBuilder okResponse() {
        return ok()
                .withHeader("Content-Type", "application/json")
                .withBody(
                        """
                        {
                            "test": "Ok"
                        }
                        """
                );
    }

    private ResponseDefinitionBuilder errorResponse() {
        return badRequest()
                .withHeader("Content-Type", "application/json")
                .withBody(
                        """
                        {
                            "message": "Service unavailable"
                        }
                        """
                );
    }
}
