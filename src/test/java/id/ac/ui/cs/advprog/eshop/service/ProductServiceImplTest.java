package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");
        product.setProductQuantity(10);
    }

    @Test
    void createProduct_success() {
        when(productRepository.create(product))
                .thenReturn(product);

        Product result = productService.create(product);

        assertEquals(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void deleteProduct_success() {
        when(productRepository.delete(product))
                .thenReturn(product);

        Product result = productService.delete(product);

        assertEquals(product, result);
        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_notFound() {
        when(productRepository.delete(product))
                .thenReturn(null);

        Product result = productService.delete(product);

        assertNull(result);
    }

    @Test
    void editProduct_success() {
        Product updated = new Product();
        updated.setProductId("1");
        updated.setProductName("Updated Laptop");
        updated.setProductQuantity(20);

        when(productRepository.edit(product))
                .thenReturn(updated);

        Product result = productService.edit(product);

        assertEquals("Updated Laptop", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void editProduct_notFound() {
        when(productRepository.edit(product))
                .thenReturn(null);

        Product result = productService.edit(product);

        assertNull(result);
    }

    @Test
    void findAllProducts_success() {
        Iterator<Product> iterator =
                Collections.singletonList(product).iterator();

        when(productRepository.findAll())
                .thenReturn(iterator);

        var result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void getProductById_success() {
        when(productRepository.getProductById("1"))
                .thenReturn(product);

        Product result = productService.getProductById("1");

        assertEquals(product, result);
    }

    @Test
    void getProductById_notFound() {
        when(productRepository.getProductById("99"))
                .thenReturn(null);

        Product result = productService.getProductById("99");

        assertNull(result);
    }
}