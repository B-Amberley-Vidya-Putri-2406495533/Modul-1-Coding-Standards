package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.controller.OrderController;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Product 1");
        product.setProductQuantity(10);
        products.add(product);

        order = new Order("order-123", products, 1708560000L, "TestAuthor");
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/createOrder"));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderHistory"));
    }

    @Test
    void testOrderHistoryPost() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findAllByAuthor("TestAuthor")).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", "TestAuthor"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderHistoryResult"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("author", "TestAuthor"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById("order-123")).thenReturn(order);

        mockMvc.perform(get("/order/pay/order-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        when(orderService.findById("order-123")).thenReturn(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);
        when(paymentService.addPayment(any(Order.class), eq("VOUCHER_CODE"), any(Map.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-123")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrderResult"))
                .andExpect(model().attributeExists("paymentId"));
    }

    @Test
    void testCreateOrderPost() throws Exception {

        mockMvc.perform(post("/order/create")
                        .param("author", "aku")
                        .param("productName", "Test Product")
                        .param("productQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }

    @Test
    void testPayOrderPostWithExtraParams() throws Exception {
        when(orderService.findById("order-123")).thenReturn(order);

        Map<String,String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = new Payment(order,"VOUCHER_CODE",paymentData);

        when(paymentService.addPayment(any(Order.class),eq("VOUCHER_CODE"),any(Map.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-123")
                        .param("method","VOUCHER_CODE")
                        .param("voucherCode","ESHOP1234ABC5678")
                        .param("extra","value"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrderResult"));
    }
}
