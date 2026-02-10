package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFindSingleProduct() {
        Product product = new Product();
        product.setProductName("Sampo A");
        product.setProductQuantity(10);

        Product saved = productRepository.create(product);

        assertNotNull(saved.getProductId());

        Iterator<Product> iterator = productRepository.findAll();

        assertTrue(iterator.hasNext());

        Product result = iterator.next();

        assertEquals("Sampo A", result.getProductName());
        assertEquals(10, result.getProductQuantity());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllWhenEmpty() {
        Iterator<Product> iterator = productRepository.findAll();

        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllMultipleProducts() {
        Product product1 = new Product();
        product1.setProductName("Sampo A");
        product1.setProductQuantity(10);

        Product product2 = new Product();
        product2.setProductName("Sampo B");
        product2.setProductQuantity(20);

        productRepository.create(product1);
        productRepository.create(product2);

        Iterator<Product> iterator = productRepository.findAll();

        assertTrue(iterator.hasNext());
        assertEquals("Sampo A", iterator.next().getProductName());

        assertTrue(iterator.hasNext());
        assertEquals("Sampo B", iterator.next().getProductName());

        assertFalse(iterator.hasNext());
    }

    @Test
    void testGetProductByIdFound() {
        Product product = new Product();
        product.setProductName("Sampo A");
        product.setProductQuantity(10);

        Product saved = productRepository.create(product);

        Product result =
                productRepository.getProductById(saved.getProductId());

        assertNotNull(result);
        assertEquals(saved.getProductId(), result.getProductId());
        assertEquals("Sampo A", result.getProductName());
    }

    @Test
    void testGetProductByIdNotFound() {
        Product result = productRepository.getProductById("unknown-id");

        assertNull(result);
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductName("Old");
        product.setProductQuantity(5);

        Product saved = productRepository.create(product);

        Product update = new Product();
        update.setProductId(saved.getProductId());
        update.setProductName("New");
        update.setProductQuantity(50);

        Product result = productRepository.edit(update);

        assertNotNull(result);
        assertEquals("New", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product product = new Product();
        product.setProductId("unknown-id");
        product.setProductName("None");
        product.setProductQuantity(0);

        Product result = productRepository.edit(product);

        assertNull(result);
    }

    @Test
    void testEditWhenRepositoryIsEmpty() {
        Product product = new Product();
        product.setProductId("any-id");
        product.setProductName("Test");
        product.setProductQuantity(1);

        Product result = productRepository.edit(product);

        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductName("Sampo A");
        product.setProductQuantity(10);

        Product saved = productRepository.create(product);

        Product deleted = productRepository.delete(saved);

        assertNotNull(deleted);
        assertEquals(saved.getProductId(), deleted.getProductId());

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductNotFound() {
        Product product = new Product();
        product.setProductId("unknown-id");

        Product result = productRepository.delete(product);

        assertNull(result);
    }

    @Test
    void testDeleteWhenRepositoryIsEmpty() {
        Product product = new Product();
        product.setProductId("any-id");

        Product result = productRepository.delete(product);

        assertNull(result);
    }

    @Test
    void testEditProductWhenOtherProductsExist() {
        Product product1 = new Product();
        product1.setProductName("A");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductName("B");
        product2.setProductQuantity(2);

        productRepository.create(product1);
        productRepository.create(product2);

        Product update = new Product();
        update.setProductId("non-existing-id");
        update.setProductName("X");
        update.setProductQuantity(99);

        Product result = productRepository.edit(update);

        assertNull(result);
    }

    @Test
    void testDeleteWhenOtherProductsExist() {
        Product product1 = new Product();
        product1.setProductName("A");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductName("B");
        product2.setProductQuantity(2);

        productRepository.create(product1);
        productRepository.create(product2);

        Product fake = new Product();
        fake.setProductId("non-existing-id");

        Product result = productRepository.delete(fake);

        assertNull(result);

        Iterator<Product> iterator = productRepository.findAll();

        assertTrue(iterator.hasNext());
        iterator.next();

        assertTrue(iterator.hasNext());
    }

    @Test
    void testGetProductByIdWhenNotFirstElement() {

        Product product1 = new Product();
        product1.setProductName("First");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductName("Second");
        product2.setProductQuantity(2);

        Product saved1 = productRepository.create(product1);
        Product saved2 = productRepository.create(product2);

        Product result =
                productRepository.getProductById(saved2.getProductId());

        assertNotNull(result);
        assertEquals("Second", result.getProductName());
    }
}