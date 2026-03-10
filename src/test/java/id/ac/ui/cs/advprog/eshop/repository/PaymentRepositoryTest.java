package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository repository;

    Payment payment;

    @BeforeEach
    void setUp() {
        repository = new PaymentRepository();

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Sample");
        product.setProductQuantity(1);

        products.add(product);

        Order order = new Order(
                "order-1",
                products,
                1708560000L,
                "Safira"
        );

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP1234ABC5678");

        payment = new Payment(order,"VOUCHER_CODE",data);
    }

    @Test
    void savePayment() {
        Payment result = repository.save(payment);

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void findPaymentById() {
        repository.save(payment);

        Payment result = repository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void findAllPayments() {
        repository.save(payment);

        List<Payment> result = repository.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void findPaymentByIdNotFound() {

        Payment result = repository.findById("unknown-id");

        assertNull(result);
    }

    @Test
    void findAllPaymentsMultiple() {

        repository.save(payment);

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP9999ABC9999");

        Payment payment2 = new Payment(payment.getOrder(),"VOUCHER_CODE",data);

        repository.save(payment2);

        List<Payment> result = repository.findAll();

        assertEquals(2,result.size());
    }
}