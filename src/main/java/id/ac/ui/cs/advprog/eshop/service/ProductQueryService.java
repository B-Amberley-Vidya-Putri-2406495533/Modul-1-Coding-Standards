package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductQueryService {
    Product getProductById(String id);
    List<Product> findAll();
}