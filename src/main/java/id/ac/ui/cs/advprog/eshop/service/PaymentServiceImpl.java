package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String VOUCHER_METHOD = "VOUCHER_CODE";
    private static final String COD_METHOD = "CASH_ON_DELIVERY";

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment(order, method, paymentData);

        if (VOUCHER_METHOD.equals(method)) {
            handleVoucherPayment(payment, paymentData);
        }
        if (COD_METHOD.equals(method)) {
            handleCODPayment(payment, paymentData);
        }

        paymentRepository.save(payment);
        return payment;
    }

    private void handleVoucherPayment(Payment payment, Map<String, String> paymentData) {
        String voucher = paymentData.get("voucherCode");

        if (voucher != null &&
                voucher.length() == 16 &&
                voucher.startsWith("ESHOP") &&
                voucher.replaceAll("[^0-9]", "").length() == 8) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("REJECTED");
        }
    }

    private void handleCODPayment(Payment payment, Map<String, String> paymentData) {

        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty() ||
                deliveryFee == null || deliveryFee.isEmpty()) {

            payment.setStatus("REJECTED");

        } else {
            payment.setStatus("PENDING");
        }
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        if ("SUCCESS".equals(status)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        }
        if ("REJECTED".equals(status)) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}