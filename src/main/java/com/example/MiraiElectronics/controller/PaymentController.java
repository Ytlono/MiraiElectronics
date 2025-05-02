package com.example.MiraiElectronics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @PostMapping("/top-up")
    public ResponseEntity<?> topUpBalance(){
        return ResponseEntity.ok(1);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(1);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/refund/{orderId}")
    public ResponseEntity<?> refundPayment(@PathVariable Long orderId){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/method")
    public ResponseEntity<?> addPaymentMethod(){
        return ResponseEntity.ok(1);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId){
        return ResponseEntity.ok(1);
    }

}
