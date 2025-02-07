package com.kltn.auth_service.payment.momo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payment/momo")
@RestController
public class MomoController {
    @Autowired
    private MomoService momoService;

    @PostMapping
    public ResponseEntity<?> testPayment(@RequestBody MomoRequest paymentRequest) {
        return ResponseEntity.ok(momoService.createPaymentRequest(paymentRequest.getAmount()));
    }

    @GetMapping("/order-status/{orderId}")
    public String checkPaymentStatus(@PathVariable String orderId) {
        String response = momoService.checkPaymentStatus(orderId);
        return response;
    }

}
