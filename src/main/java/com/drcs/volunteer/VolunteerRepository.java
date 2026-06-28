package com.drcs.volunteer;

import com.drcs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerProfile, UUID> {

    Optional<VolunteerProfile> findByUser(User user);

    List<VolunteerProfile> findByIsAvailableAndVerificationStatus(boolean isAvailable, VerificationStatus status);

    List<VolunteerProfile> findByVerificationStatus(VerificationStatus status);
}