package com.drcs.helprequest;

import com.drcs.common.audit.BaseAuditableEntity;
import com.drcs.disaster.Disaster;
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
@Table(name = "help_requests")
public class HelpRequest extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disaster_id")
    private Disaster disaster;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HelpCategory category;

    @Column(name = "priority_score", nullable = false)
    private Integer priorityScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequestStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "family_members_count", nullable = false)
    private Integer familyMembersCount;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_volunteer_id")
    private User assignedVolunteer;
}