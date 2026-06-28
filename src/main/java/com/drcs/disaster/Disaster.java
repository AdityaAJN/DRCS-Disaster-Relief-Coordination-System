package com.drcs.disaster;

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
@Table(name = "disasters")
public class Disaster extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", nullable = false, length = 20)
    private SeverityLevel severityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DisasterStatus status;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "radius_km", nullable = false)
    private Double radiusKm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}