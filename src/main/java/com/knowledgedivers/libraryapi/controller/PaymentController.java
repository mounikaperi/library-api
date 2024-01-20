package com.knowledgedivers.libraryapi.controller;

import com.knowledgedivers.libraryapi.requestmodels.PaymentInfoRequest;
import com.knowledgedivers.libraryapi.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {
    private PaymentService paymentService;

    private PaymentInfoRequest paymentInfoRequest;

    @Autowired
    public PaymentController(PaymentService paymentService, PaymentInfoRequest paymentInfoRequest) {
        this.paymentService = paymentService;
        this.paymentInfoRequest = paymentInfoRequest;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws Exception {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentResponse = paymentIntent.toJson();
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
    
}
