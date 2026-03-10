package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                product.getProductId()
        );
    }

    @Test
    void testGetProductName() {
        assertEquals(
                "Sampo Cap Bambang",
                product.getProductName()
        );
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(
                100,
                product.getProductQuantity()
        );
    }

    @Test
    void testSetters() {

        Product p = new Product();

        p.setProductId("p1");
        p.setProductName("Keyboard");
        p.setProductQuantity(20);

        assertEquals("p1", p.getProductId());
        assertEquals("Keyboard", p.getProductName());
        assertEquals(20, p.getProductQuantity());
    }

    @Test
    void testDefaultValues() {

        Product p = new Product();

        assertNull(p.getProductId());
        assertNull(p.getProductName());
        assertEquals(0, p.getProductQuantity());
    }
}