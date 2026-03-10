package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return "order/createOrder";
    }
    @PostMapping("/create")
    public String createOrder(@RequestParam String author,
                              @RequestParam String productName,
                              @RequestParam int productQuantity) {

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName(productName);
        product.setProductQuantity(productQuantity);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Order order = new Order(
                UUID.randomUUID().toString(),
                products,
                System.currentTimeMillis(),
                author
        );

        orderService.createOrder(order);

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistoryPage(Model model) {
        return "order/orderHistory";
    }

    @PostMapping("/history")
    public String orderHistoryPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "order/orderHistoryResult";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/payOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId,
                               @RequestParam String method,
                               @RequestParam Map<String, String> allParams,
                               Model model) {
        Order order = orderService.findById(orderId);
        Map<String, String> paymentData = new HashMap<>(allParams);
        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("paymentId", payment.getId());
        return "order/payOrderResult";
    }
}
