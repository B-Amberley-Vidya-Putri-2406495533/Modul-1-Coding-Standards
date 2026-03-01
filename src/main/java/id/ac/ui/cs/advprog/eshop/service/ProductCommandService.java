package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductCommandService {
    Product create(Product product);
    Product edit(Product product);
    Product delete(Product product);
}