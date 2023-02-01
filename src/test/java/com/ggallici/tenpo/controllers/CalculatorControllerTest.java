package com.ggallici.tenpo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggallici.tenpo.dtos.add.AddRequestDto;
import com.ggallici.tenpo.filters.RateLimitFilter;
import com.ggallici.tenpo.filters.TransactionLogFilter;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.services.CalculatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CalculatorController.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CalculatorMapper calculatorMapper;
    @Autowired
    private RateLimitFilter rateLimitFilter;
    @Autowired
    private TransactionLogFilter transactionLogFilter;

    @MockBean
    private CalculatorService calculatorServiceMock;

    @Test
    public void add() throws Exception {
        var errorParam = false; var body = new AddRequestDto(BigDecimal.ONE, BigDecimal.ZERO);

        doReturn(BigDecimal.ONE).when(calculatorServiceMock).add(body.first(), body.second(), errorParam);

        mockMvc.perform(post("/calculator/adder")
                .contentType("application/json")
                .param("error", objectMapper.writeValueAsString(errorParam))
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }
}
