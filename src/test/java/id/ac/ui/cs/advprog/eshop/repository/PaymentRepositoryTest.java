package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sample Product");
        product.setProductQuantity(1);

        products.add(product);

        Order order = new Order(
                "order-1",
                products,
                123456L,
                "Safira"
        );

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(order, "VOUCHER_CODE", paymentData);
    }

    @Test
    void testSavePayment() {
        Payment result = paymentRepository.save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindPaymentById() {
        paymentRepository.save(payment);
        Payment result = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindAllPayments() {
        paymentRepository.save(payment);
        assertEquals(1, paymentRepository.findAll().size());
    }
}