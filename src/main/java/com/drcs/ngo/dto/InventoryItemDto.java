package com.drcs.ngo.dto;

import com.drcs.ngo.InventoryCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemDto {

    private UUID id;
    private UUID ngoId;
    private String ngoName;
    private String itemName;
    private InventoryCategory category;
    private Integer quantity;
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}