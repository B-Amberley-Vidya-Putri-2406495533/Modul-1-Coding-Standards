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

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment(order, method, paymentData);

        if (method.equals("VOUCHER_CODE")) {

            String voucher = paymentData.get("voucherCode");

            if (voucher != null &&
                    voucher.length() == 16 &&
                    voucher.startsWith("ESHOP") &&
                    voucher.replaceAll("[^0-9]", "").length() == 8) {

                payment.setStatus("SUCCESS");

            } else {
                payment.setStatus("REJECTED");
            }

        } else if (method.equals("CASH_ON_DELIVERY")) {

            String address = paymentData.get("address");
            String fee = paymentData.get("deliveryFee");

            if (address == null || address.isEmpty() ||
                    fee == null || fee.isEmpty()) {

                payment.setStatus("REJECTED");

            } else {
                payment.setStatus("PENDING");
            }
        }

        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        if (status.equals("SUCCESS")) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        }

        if (status.equals("REJECTED")) {
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