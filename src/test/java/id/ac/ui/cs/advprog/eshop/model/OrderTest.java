package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            new Order(
                    "13652556-012a-4c07-b546-54eb1396d79b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat"
            );
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat",
                OrderStatus.SUCCESS.getValue()
        );

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order(
                    "13652556-012a-4c07-b546-54eb1396d79b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat",
                    "MEOW"
            );
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        order.setStatus(OrderStatus.CANCELLED.getValue());
        assertEquals(OrderStatus.CANCELLED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }

    @Test
    void testGetters() {
        Order order = new Order(
                "order-123",
                products,
                123456L,
                "Safira"
        );

        assertEquals("order-123", order.getId());
        assertEquals(products, order.getProducts());
        assertEquals(123456L, order.getOrderTime());
        assertEquals("Safira", order.getAuthor());
    }

    @Test
    void testOrderBuilder() {
        Order order = Order.builder()
                .id("order-builder")
                .products(products)
                .orderTime(999L)
                .author("Builder Test")
                .status(OrderStatus.SUCCESS.getValue())
                .build();

        assertEquals("order-builder", order.getId());
        assertEquals(products, order.getProducts());
        assertEquals(999L, order.getOrderTime());
        assertEquals("Builder Test", order.getAuthor());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testOrderBuilderDefaultStatus() {

        List<Product> builderProducts = new ArrayList<>();

        Product product = new Product();
        product.setProductId("builder-product");
        product.setProductName("Builder Product");
        product.setProductQuantity(1);

        builderProducts.add(product);

        Order order = Order.builder()
                .id("order-builder-2")
                .products(builderProducts)
                .orderTime(111L)
                .author("Builder Default")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();

        assertEquals("order-builder-2", order.getId());
        assertEquals(builderProducts, order.getProducts());
        assertEquals(111L, order.getOrderTime());
        assertEquals("Builder Default", order.getAuthor());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testOrderBuilderToString() {

        List<Product> builderProducts = new ArrayList<>();

        Product product = new Product();
        product.setProductId("builder-product");
        product.setProductName("Builder Product");
        product.setProductQuantity(1);

        builderProducts.add(product);

        String result = Order.builder()
                .id("order-builder")
                .products(builderProducts)
                .orderTime(1L)
                .author("Tester")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .toString();

        assertNotNull(result);
    }
}