package com.drcs.ngo.service;

import com.drcs.exception.ResourceNotFoundException;
import com.drcs.ngo.InventoryCategory;
import com.drcs.ngo.InventoryItem;
import com.drcs.ngo.InventoryRepository;
import com.drcs.ngo.dto.CreateInventoryItemRequest;
import com.drcs.ngo.dto.InventoryItemDto;
import com.drcs.ngo.mapper.InventoryMapper;
import com.drcs.user.User;
import com.drcs.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public InventoryItemDto addInventoryItem(CreateInventoryItemRequest request, String ngoEmail) {
        User ngo = userRepository.findByEmail(ngoEmail)
                .orElseThrow(() -> new ResourceNotFoundException("NGO user not found: " + ngoEmail));

        InventoryItem item = InventoryItem.builder()
                .ngo(ngo)
                .itemName(request.getItemName())
                .category(request.getCategory())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .build();

        InventoryItem saved = inventoryRepository.save(item);
        return inventoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public InventoryItemDto updateInventoryItem(UUID itemId, CreateInventoryItemRequest request) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with ID: " + itemId));

        item.setItemName(request.getItemName());
        item.setCategory(request.getCategory());
        item.setQuantity(request.getQuantity());
        item.setUnit(request.getUnit());

        InventoryItem updated = inventoryRepository.save(item);
        return inventoryMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryItemDto getInventoryItemById(UUID itemId) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with ID: " + itemId));
        return inventoryMapper.toDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemDto> getMyInventory(String ngoEmail) {
        User ngo = userRepository.findByEmail(ngoEmail)
                .orElseThrow(() -> new ResourceNotFoundException("NGO user not found: " + ngoEmail));

        return inventoryRepository.findByNgo(ngo).stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemDto> getInventoryByCategory(InventoryCategory category) {
        return inventoryRepository.findByCategory(category).stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteInventoryItem(UUID itemId) {
        InventoryItem item = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with ID: " + itemId));
        inventoryRepository.delete(item);
    }
}