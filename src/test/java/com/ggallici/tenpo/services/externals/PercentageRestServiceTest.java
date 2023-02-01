package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.exceptions.RestServiceException;
import com.ggallici.tenpo.services.wrappers.RestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static com.ggallici.tenpo.services.externals.PercentageRestService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PercentageRestServiceTest {
    @Mock
    private RestService restServiceMock;

    private PercentageRestService percentageRestService;

    @Before
    public void setUp() {
        this.percentageRestService = new PercentageRestService(restServiceMock);
    }

    @Test
    public void testGet_ok() {
        var expected = new PercentageResponseDto(BigDecimal.TEN);

        doReturn(expected).when(restServiceMock).getObject(SERVICE, OK_URI, PercentageResponseDto.class);

        var retrieved = percentageRestService.get(false);

        assertThat(retrieved).isEqualTo(expected);
        verify(restServiceMock).getObject(SERVICE, OK_URI, PercentageResponseDto.class);
    }

    @Test
    public void testGet_error() {
        var expected = new RestServiceException("test", HttpStatus.SERVICE_UNAVAILABLE, null);

        doThrow(expected).when(restServiceMock).getObject(SERVICE, ERROR_URI, PercentageResponseDto.class);

        var retrieved = catchException(() -> percentageRestService.get(true));

        assertThat(retrieved).isEqualTo(expected);
        verify(restServiceMock).getObject(SERVICE, ERROR_URI, PercentageResponseDto.class);
    }
}
