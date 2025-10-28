package com.neighbourhoodwatch.api.subscription;

import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubscriptionDTO createSubscription(String memberUUID, Subscription.SubscriptionType type, Subscription.SubscriptionStatus status, LocalDate startDate, LocalDate endDate) {
        User member = userRepository.findByUserUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = Subscription.builder()
                .member(member)
                .type(type)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        subscriptionRepository.save(sub);

        return mapToDTO(sub);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionDTO getSubscriptionByUUID(String uuid) {
        Subscription sub = subscriptionRepository.findBySubscriptionUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        return mapToDTO(sub);
    }

    @Transactional
    public SubscriptionDTO updateSubscription(String uuid, Subscription.SubscriptionType type, Subscription.SubscriptionStatus status, LocalDate endDate) {
        Subscription sub = subscriptionRepository.findBySubscriptionUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (type != null) sub.setType(type);
        if (status != null) sub.setStatus(status);
        if (endDate != null) sub.setEndDate(endDate);

        return mapToDTO(sub);
    }

    @Transactional
    public void deleteSubscription(String uuid) {
        Subscription sub = subscriptionRepository.findBySubscriptionUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscriptionRepository.delete(sub);
    }

    private SubscriptionDTO mapToDTO(Subscription sub) {
        return new SubscriptionDTO(
                sub.getSubscriptionUUID(),
                sub.getMember().getUserUUID(),
                sub.getStatus().name(),
                sub.getType().name(),
                sub.getStartDate(),
                sub.getEndDate(),
                sub.getCreatedAt(),
                sub.getUpdatedAt()
        );
    }
}
