package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        Order existingOrder = orderRepository.findById(order.getId());

        if (existingOrder != null) {
            return null;
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new NoSuchElementException();
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return orderRepository.findAllByAuthor(author);
    }
}