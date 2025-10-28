package com.neighbourhoodwatch.api.housemember;

import com.neighbourhoodwatch.api.house.House;
import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "house_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "house_id"}),
                @UniqueConstraint(columnNames = "houseMemberUUID")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseMemberId;

    @Column(nullable = false, unique = true, length = 36)
    private String houseMemberUUID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_house_member_user"))
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_house_member_house"))
    private House house;

    @Column(nullable = false, length = 50)
    private String relationship;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.houseMemberUUID == null) {
            this.houseMemberUUID = UUID.randomUUID().toString();
        }
    }
}
