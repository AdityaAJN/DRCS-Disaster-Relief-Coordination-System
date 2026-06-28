package com.drcs.shelter;

import com.drcs.common.audit.BaseAuditableEntity;
import com.drcs.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shelters")
public class Shelter extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "total_capacity", nullable = false)
    private Integer totalCapacity;

    @Builder.Default
    @Column(name = "current_occupancy", nullable = false)
    private Integer currentOccupancy = 0;

    @Builder.Default
    @Column(name = "has_food", nullable = false)
    private boolean hasFood = true;

    @Builder.Default
    @Column(name = "has_medical", nullable = false)
    private boolean hasMedical = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by")
    private User managedBy;
}
