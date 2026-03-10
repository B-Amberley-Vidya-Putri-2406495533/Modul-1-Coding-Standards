package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    Order order;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();

        order = new Order(
                "order-1",
                null,
                123456L,
                "Safira"
        );
    }

    @Test
    void testCreatePayment() {

        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertNotNull(payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
    }

}