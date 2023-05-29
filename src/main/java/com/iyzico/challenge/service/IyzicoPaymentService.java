package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.model.BankPaymentRequest;
import com.iyzico.challenge.model.BankPaymentResponse;
import com.iyzico.challenge.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class IyzicoPaymentService {

    private final BankService bankService;
    private final PaymentRepository paymentRepository;

    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        //insert records
        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);
        paymentRepository.save(payment);
        log.info("Payment saved successfully!");
    }
}
