package com.drcs.helprequest;

import com.drcs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, UUID> {

    List<HelpRequest> findByCitizen(User citizen);

    List<HelpRequest> findByAssignedVolunteer(User volunteer);

    List<HelpRequest> findByStatus(RequestStatus status);

    List<HelpRequest> findByStatusOrderByPriorityScoreDesc(RequestStatus status);
}