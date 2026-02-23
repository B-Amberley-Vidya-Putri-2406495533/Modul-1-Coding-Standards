package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void productList_success() throws Exception {

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");
        product.setProductQuantity(10);

        when(productService.findAll())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void createProductPage_success() throws Exception {

        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_success() throws Exception {

        mockMvc.perform(post("/product/create")
                        .param("productId", "1")
                        .param("productName", "Laptop")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void deleteProduct_success() throws Exception {

        mockMvc.perform(post("/product/delete")
                        .param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void editProductPage_success() throws Exception {

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");
        product.setProductQuantity(10);

        when(productService.getProductById("1"))
                .thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void editProductPage_notFound() throws Exception {

        when(productService.getProductById("99"))
                .thenReturn(null);

        mockMvc.perform(get("/product/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void editProductPost_success() throws Exception {

        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Updated Laptop")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }
}