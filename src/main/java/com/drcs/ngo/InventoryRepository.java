package com.drcs.ngo;

import com.drcs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Data Access Repository for InventoryItem entities.
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, UUID> {

    List<InventoryItem> findByNgo(User ngo);

    List<InventoryItem> findByCategory(InventoryCategory category);
}