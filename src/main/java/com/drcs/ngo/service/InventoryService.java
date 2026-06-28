package com.drcs.ngo.service;

import com.drcs.ngo.InventoryCategory;
import com.drcs.ngo.dto.CreateInventoryItemRequest;
import com.drcs.ngo.dto.InventoryItemDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    InventoryItemDto addInventoryItem(CreateInventoryItemRequest request, String ngoEmail);

    InventoryItemDto updateInventoryItem(UUID itemId, CreateInventoryItemRequest request);

    InventoryItemDto getInventoryItemById(UUID itemId);

    List<InventoryItemDto> getMyInventory(String ngoEmail);

    List<InventoryItemDto> getAllInventory();

    List<InventoryItemDto> getInventoryByCategory(InventoryCategory category);

    void deleteInventoryItem(UUID itemId);
}