package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.controller.PaymentController;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment payment;
    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Product 1");
        product.setProductQuantity(10);
        products.add(product);

        order = new Order("order-123", products, 1708560000L, "TestAuthor");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order, "VOUCHER_CODE", paymentData);
    }

    @Test
    void testPaymentDetailFormNoParam() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetailForm"));
    }

    @Test
    void testPaymentDetailFormWithParam() throws Exception {
        mockMvc.perform(get("/payment/detail")
                        .param("paymentId", "pay-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/detail/pay-1"));
    }

    @Test
    void testPaymentDetail() throws Exception {
        when(paymentService.getPayment(payment.getId())).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminPaymentList() throws Exception {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentAdminList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testAdminPaymentDetail() throws Exception {
        when(paymentService.getPayment(payment.getId())).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentAdminDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testSetPaymentStatusSuccess() throws Exception {
        when(paymentService.getPayment(payment.getId())).thenReturn(payment);
        when(paymentService.setStatus(payment, "SUCCESS")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/" + payment.getId())
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/" + payment.getId()));
    }

    @Test
    void testSetPaymentStatusRejected() throws Exception {
        when(paymentService.getPayment(payment.getId())).thenReturn(payment);
        when(paymentService.setStatus(payment, "REJECTED")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/" + payment.getId())
                        .param("status", "REJECTED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/" + payment.getId()));
    }
}
