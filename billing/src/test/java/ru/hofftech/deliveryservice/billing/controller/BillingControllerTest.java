package ru.hofftech.deliveryservice.billing.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hofftech.deliveryservice.billing.enums.Operation;
import ru.hofftech.deliveryservice.billing.model.dto.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.billing.service.BillingService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Testcontainers
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer("latest");

    @BeforeAll
    static void beforeAll() {
        kafkaContainer.start();
        postgresqlContainer.start();
        System.setProperty("spring.datasource.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresqlContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresqlContainer.getPassword());
        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
    }

    @Test
    public void findByUserPeriod_validInput_shouldReturnResponse() throws Exception {
        String user = "user123";
        LocalDateTime createdAt = LocalDateTime.parse("2025-02-12T00:03:01", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<BillingAuditResponseDto> mockResponses = List.of(
                new BillingAuditResponseDto(
                        createdAt,
                        Operation.LOAD_PARCELS.getOperationName(),
                        3,
                        1,
                        BigDecimal.valueOf(800)));

        when(billingService.findBillingAuditRecordsByUser(user)).thenReturn(mockResponses);

        var uriTemplate = String.format("/api/v1/billing?user=%s", user);

        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].createdAt").value(createdAt.toString()))
                .andExpect(jsonPath("$[0].operationType").value(Operation.LOAD_PARCELS.getOperationName()))
                .andExpect(jsonPath("$[0].parcelsCount").value(3))
                .andExpect(jsonPath("$[0].trucksCount").value(1))
                .andExpect(jsonPath("$[0].amount").value(BigDecimal.valueOf(800)));

        verify(billingService, times(1)).findBillingAuditRecordsByUser(user);
    }

    @Test
    public void findByUserPeriod_validInputWithPeriod_shouldReturnResponse() throws Exception {
        String user = "user123";
        LocalDate startDate = LocalDate.parse("2025-02-10", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse("2025-02-12", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime createdAt = LocalDateTime.parse("2025-02-12T00:03:01", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<BillingAuditResponseDto> mockResponses = List.of(
                new BillingAuditResponseDto(
                        createdAt,
                        Operation.LOAD_PARCELS.getOperationName(),
                        3,
                        1,
                        BigDecimal.valueOf(800)));

        when(billingService.findBillingAuditRecordsByUserForPeriod(user, startDate, endDate)).thenReturn(mockResponses);

        var uriTemplate = String.format("/api/v1/billing?user=%s&startDate=%s&endDate=%s", user, startDate, endDate);

        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].createdAt").value(createdAt.toString()))
                .andExpect(jsonPath("$[0].operationType").value(Operation.LOAD_PARCELS.getOperationName()))
                .andExpect(jsonPath("$[0].parcelsCount").value(3))
                .andExpect(jsonPath("$[0].trucksCount").value(1))
                .andExpect(jsonPath("$[0].amount").value(BigDecimal.valueOf(800)));

        verify(billingService, times(1)).findBillingAuditRecordsByUserForPeriod(user, startDate, endDate);
    }

    @Test
    public void findByUserPeriod_validInputWithMonthPeriod_shouldReturnResponse() throws Exception {
        String user = "user123";
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        LocalDateTime createdAt = LocalDateTime.parse("2025-02-12T00:03:01", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<BillingAuditResponseDto> mockResponses = List.of(
                new BillingAuditResponseDto(
                        createdAt,
                        Operation.LOAD_PARCELS.getOperationName(),
                        3,
                        1,
                        BigDecimal.valueOf(800)));

        when(billingService.findBillingAuditRecordsByUserForLastMonth(user)).thenReturn(mockResponses);

        var uriTemplate = String.format("/api/v1/billing?user=%s&startDate=%s&endDate=%s", user, startDate, endDate);

        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].createdAt").value(createdAt.toString()))
                .andExpect(jsonPath("$[0].operationType").value(Operation.LOAD_PARCELS.getOperationName()))
                .andExpect(jsonPath("$[0].parcelsCount").value(3))
                .andExpect(jsonPath("$[0].trucksCount").value(1))
                .andExpect(jsonPath("$[0].amount").value(BigDecimal.valueOf(800)));

        verify(billingService, times(1)).findBillingAuditRecordsByUserForLastMonth(user);
    }
}