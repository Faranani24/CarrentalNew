package co.za.carrental.controller;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentMethod;
import co.za.carrental.domain.PaymentStatus;
import co.za.carrental.factory.PaymentFactory;
import co.za.carrental.repository.PaymentRepository;
import co.za.carrental.service.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();

        testPayment = PaymentFactory.buildPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("1000.00"),
                PaymentMethod.CREDIT_CARD,
                PaymentStatus.COMPLETED
        );
        paymentService.createPayment(testPayment);
    }

    @Test
    void create_shouldCreatePayment() throws Exception {
        Payment newPayment = PaymentFactory.buildPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("500.00"),
                PaymentMethod.EFT,
                PaymentStatus.PENDING
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPayment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(500.00));
    }

    @Test
    void read_shouldReturnPaymentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/{id}", testPayment.getPaymentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.00));
    }

    @Test
    void update_shouldUpdatePayment() throws Exception {
        Payment updatedPayment = Payment.builder()
                .paymentId(testPayment.getPaymentId())
                .bookingId(testPayment.getBookingId())
                .customerId(testPayment.getCustomerId())
                .paymentMethod(testPayment.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .amount(new BigDecimal("1200.00"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/payments/{id}", updatedPayment.getPaymentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1200.00));
    }

    @Test
    void delete_shouldDeletePayment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/payments/{id}", testPayment.getPaymentId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll_shouldReturnAllPayments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
