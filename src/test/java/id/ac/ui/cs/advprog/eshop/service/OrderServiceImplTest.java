package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
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
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    Order order;
    List<Product> products;

    @BeforeEach
    void setUp() {

        products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sample Product");
        product.setProductQuantity(2);

        products.add(product);

        order = new Order(
                "order-1",
                products,
                1708560000L,
                "Safira"
        );
    }

    @Test
    void testCreateOrderSuccess() {

        when(orderRepository.findById(order.getId())).thenReturn(null);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());

        verify(orderRepository).save(order);
    }

    @Test
    void testCreateOrderAlreadyExists() {

        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNull(result);

        verify(orderRepository, never()).save(any());
    }

    @Test
    void testUpdateStatusSuccess() {

        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateStatus(
                order.getId(),
                OrderStatus.SUCCESS.getValue()
        );

        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {

        when(orderRepository.findById(order.getId())).thenReturn(order);

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "INVALID")
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void testUpdateStatusOrderNotFound() {

        when(orderRepository.findById("unknown")).thenReturn(null);

        assertThrows(
                NoSuchElementException.class,
                () -> orderService.updateStatus(
                        "unknown",
                        OrderStatus.SUCCESS.getValue()
                )
        );
    }

    @Test
    void testFindByIdFound() {

        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.findById(order.getId());

        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {

        when(orderRepository.findById("unknown")).thenReturn(null);

        Order result = orderService.findById("unknown");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthorFound() {

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.findAllByAuthor(order.getAuthor())).thenReturn(orders);

        List<Order> results = orderService.findAllByAuthor(order.getAuthor());

        assertEquals(1, results.size());
        assertEquals(order.getAuthor(), results.get(0).getAuthor());
    }

    @Test
    void testFindAllByAuthorEmpty() {

        when(orderRepository.findAllByAuthor("unknown")).thenReturn(new ArrayList<>());

        List<Order> results = orderService.findAllByAuthor("unknown");

        assertTrue(results.isEmpty());
    }
}