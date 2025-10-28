package com.neighbourhoodwatch.api.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{memberUUID}")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @PathVariable String memberUUID,
            @RequestParam Subscription.SubscriptionType type,
            @RequestParam Subscription.SubscriptionStatus status,
            @RequestParam LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                subscriptionService.createSubscription(memberUUID, type, status, startDate, endDate)
        );
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/{subscriptionUUID}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionByUUID(@PathVariable String subscriptionUUID) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByUUID(subscriptionUUID));
    }

    @PutMapping("/{subscriptionUUID}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @PathVariable String subscriptionUUID,
            @RequestParam(required = false) Subscription.SubscriptionType type,
            @RequestParam(required = false) Subscription.SubscriptionStatus status,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                subscriptionService.updateSubscription(subscriptionUUID, type, status, endDate)
        );
    }

    @DeleteMapping("/{subscriptionUUID}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String subscriptionUUID) {
        subscriptionService.deleteSubscription(subscriptionUUID);
        return ResponseEntity.noContent().build();
    }
}
