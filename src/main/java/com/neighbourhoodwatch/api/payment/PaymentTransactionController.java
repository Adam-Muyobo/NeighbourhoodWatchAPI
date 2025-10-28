package com.neighbourhoodwatch.api.payment;

import com.neighbourhoodwatch.api.payment.dto.PaymentTransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentService;

    @PostMapping("/{subscriptionUUID}")
    public ResponseEntity<PaymentTransactionDTO> createPayment(
            @PathVariable String subscriptionUUID,
            @RequestParam BigDecimal amount,
            @RequestParam PaymentTransaction.PaymentMethod paymentMethod,
            @RequestParam String reference
    ) {
        return ResponseEntity.ok(paymentService.createPayment(subscriptionUUID, amount, paymentMethod, reference));
    }

    @GetMapping
    public ResponseEntity<List<PaymentTransactionDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentUUID}")
    public ResponseEntity<PaymentTransactionDTO> getPaymentByUUID(@PathVariable String paymentUUID) {
        return ResponseEntity.ok(paymentService.getPaymentByUUID(paymentUUID));
    }

    @PutMapping("/{paymentUUID}")
    public ResponseEntity<PaymentTransactionDTO> updatePayment(
            @PathVariable String paymentUUID,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) PaymentTransaction.PaymentMethod paymentMethod
    ) {
        return ResponseEntity.ok(paymentService.updatePayment(paymentUUID, amount, paymentMethod));
    }

    @DeleteMapping("/{paymentUUID}")
    public ResponseEntity<Void> deletePayment(@PathVariable String paymentUUID) {
        paymentService.deletePayment(paymentUUID);
        return ResponseEntity.noContent().build();
    }
}