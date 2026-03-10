package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    Order order;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {

        paymentData = new HashMap<>();

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sample Product");
        product.setProductQuantity(1);

        products.add(product);

        order = new Order(
                "order-1",
                products,
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
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testSetPaymentStatus() {

        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAllGetters() {

        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        String id = payment.getId();
        String method = payment.getMethod();
        Map<String, String> data = payment.getPaymentData();
        Order retrievedOrder = payment.getOrder();
        String status = payment.getStatus();

        assertNotNull(id);
        assertEquals("VOUCHER_CODE", method);
        assertEquals(paymentData, data);
        assertEquals(order, retrievedOrder);
        assertEquals("PENDING", status);
    }
}