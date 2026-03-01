package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarCommandService {
    public Car create(Car car);
    public void update(String carId, Car car);
    public void deleteCarById(String carId);
}
