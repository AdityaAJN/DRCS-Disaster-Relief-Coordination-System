package com.drcs.ngo.mapper;

import com.drcs.ngo.InventoryItem;
import com.drcs.ngo.dto.InventoryItemDto;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryItemDto toDto(InventoryItem item) {
        if (item == null) {
            return null;
        }

        return InventoryItemDto.builder()
                .id(item.getId())
                .ngoId(item.getNgo() != null ? item.getNgo().getId() : null)
                .ngoName(item.getNgo() != null ? item.getNgo().getFullName() : null)
                .itemName(item.getItemName())
                .category(item.getCategory())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}