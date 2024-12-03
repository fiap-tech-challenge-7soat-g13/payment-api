package com.fiap.challenge.payment.app.adapter.input.web.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.*;
import com.fiap.challenge.payment.app.adapter.input.web.payment.mapper.OrderRequestMapper;
import com.fiap.challenge.payment.app.adapter.input.web.payment.mapper.PaymentResponseMapper;
import com.fiap.challenge.payment.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.OrderStatus;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import com.fiap.challenge.payment.core.usecases.payment.PaymentCreateUseCase;
import com.fiap.challenge.payment.core.usecases.payment.PaymentGetUseCase;
import com.fiap.challenge.payment.core.usecases.payment.PaymentVerifyUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerIntegrationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentCreateUseCase paymentCreateUseCase;

    @MockBean
    private PaymentGetUseCase paymentGetUseCase;

    @MockBean
    private PaymentVerifyUseCase paymentVerifyUseCase;

    @MockBean
    private OrderRequestMapper orderRequestMapper;

    @MockBean
    private PaymentResponseMapper paymentResponseMapper;

    @Test
    void shouldCreate() throws Exception {

        OrderRequest orderRequest = createOrderRequest();

        Order order = new Order();
        Payment payment = new Payment();
        PaymentResponse paymentResponse = createPaymentResponse(1L);

        when(orderRequestMapper.toOrder(any(OrderRequest.class))).thenReturn(order);
        when(paymentCreateUseCase.execute(order)).thenReturn(payment);
        when(paymentResponseMapper.toPaymentResponse(payment)).thenReturn(paymentResponse);

        mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON).content(toJson(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(paymentResponse)));

        ArgumentCaptor<OrderRequest> orderRequestCaptor = ArgumentCaptor.forClass(OrderRequest.class);

        verify(orderRequestMapper).toOrder(orderRequestCaptor.capture());
        verify(paymentCreateUseCase).execute(order);
        verify(paymentResponseMapper).toPaymentResponse(payment);

        assertThat(orderRequest).usingRecursiveComparison().isEqualTo(orderRequestCaptor.getValue());
    }

    @Test
    void shouldGet() throws Exception {

        Long id = 1L;

        Payment payment = new Payment();
        PaymentResponse paymentResponse = createPaymentResponse(id);

        when(paymentGetUseCase.execute(id)).thenReturn(payment);
        when(paymentResponseMapper.toPaymentResponse(payment)).thenReturn(paymentResponse);

        mockMvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(paymentResponse)));

        verify(paymentGetUseCase).execute(id);
        verify(paymentResponseMapper).toPaymentResponse(payment);
    }

    @Test
    void shouldNotGet() throws Exception {

        Long id = 1L;

        when(paymentGetUseCase.execute(id)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/{id}", id)).andExpect(status().isNotFound());

        verify(paymentGetUseCase).execute(id);
        verify(paymentResponseMapper, never()).toPaymentResponse(any());
    }

    @Test
    void shouldReceiveCallback() throws Exception {

        String id = "id";
        String topic = "payment";
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(post("/{uuid}/callback", uuid).param("id", id).param("topic", topic))
                .andExpect(status().isOk());

        verify(paymentVerifyUseCase).execute(uuid, id);
    }

    @Test
    void shouldNotReceiveCallback() throws Exception {

        String id = "id";
        String topic = "unknown";
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(post("/{uuid}/callback", uuid).param("id", id).param("topic", topic))
                .andExpect(status().isOk());

        verify(paymentVerifyUseCase, never()).execute(uuid, id);
    }

    private static OrderRequest createOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setStatus(OrderStatus.CRIADO);
        orderRequest.setCreatedAt(LocalDateTime.now());
        orderRequest.setTotal(BigDecimal.TWO);
        orderRequest.setCustomer(createCustomerRequest());
        orderRequest.setProducts(List.of(createOrderProductRequest()));
        return orderRequest;
    }

    private static CustomerRequest createCustomerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setId(UUID.fromString("670104bb-eac6-4bb1-ae7f-df2cdd60d9ba"));
        customerRequest.setName("Bill Gates");
        customerRequest.setDocument("44867508020");
        customerRequest.setEmail("bill.gates@microsoft.com");
        return customerRequest;
    }

    private static OrderProductRequest createOrderProductRequest() {
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setId(1L);
        orderProductRequest.setQuantity(2);
        orderProductRequest.setPrice(BigDecimal.ONE);
        orderProductRequest.setProduct(createProductRequest());
        return orderProductRequest;
    }

    private static ProductRequest createProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("X-burger");
        return productRequest;
    }

    private static PaymentResponse createPaymentResponse(Long id) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(id);
        paymentResponse.setQrCode("qrCode");
        paymentResponse.setStatus(PaymentStatus.PENDENTE);
        return paymentResponse;
    }

    private String toJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

}