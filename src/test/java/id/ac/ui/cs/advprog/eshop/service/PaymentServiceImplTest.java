package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    Order order;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sample Product");
        product.setProductQuantity(1);

        products.add(product);

        order = new Order(
                "order-1",
                products,
                1708560000L,
                "Safira"
        );

        paymentData = new HashMap<>();
    }

    @Test
    void testAddPaymentVoucherValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentVoucherInvalid() {
        paymentData.put("voucherCode", "INVALID");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentVoucherNullCode() {
        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentCashOnDeliveryValid() {
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "10000");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("PENDING", payment.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentCODInvalidAddress() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentCODInvalidDeliveryFee() {
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentCODNullAddress() {
        paymentData.put("deliveryFee", "10000");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentCODNullDeliveryFee() {
        paymentData.put("address", "Jakarta");

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentUnknownMethod() {
        Payment payment = paymentService.addPayment(order, "UNKNOWN", paymentData);

        assertNotNull(payment);

        verify(paymentRepository).save(payment);
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        paymentService.setStatus(payment, "SUCCESS");

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        paymentService.setStatus(payment, "REJECTED");

        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusOtherValue() {
        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        paymentService.setStatus(payment, "PENDING");

        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testGetPayment() {

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {

        when(paymentRepository.findAll()).thenReturn(List.of());
        List<Payment> payments = paymentService.getAllPayments();

        assertTrue(payments.isEmpty());
    }

    @Test
    void testAddPaymentVoucherInvalidLength() {
        paymentData.put("voucherCode", "ESHOP123");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentVoucherInvalidPrefix() {
        paymentData.put("voucherCode", "WRONG12345678ABC");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPaymentVoucherInvalidDigitCount() {
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository).save(payment);
    }
}