package com.drcs.ngo.controller;

import com.drcs.common.response.ApiResponse;
import com.drcs.ngo.InventoryCategory;
import com.drcs.ngo.dto.CreateInventoryItemRequest;
import com.drcs.ngo.dto.InventoryItemDto;
import com.drcs.ngo.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "NGO Inventory API", description = "Endpoints for NGO relief materials, stockpile tracking, and resource availability")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @PreAuthorize("hasRole('NGO') or hasRole('ADMIN')")
    @Operation(summary = "Add a new inventory stockpile item (NGO/Admin)")
    public ResponseEntity<ApiResponse<InventoryItemDto>> addInventoryItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateInventoryItemRequest request) {
        InventoryItemDto item = inventoryService.addInventoryItem(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(item, "Inventory item registered successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('NGO') or hasRole('ADMIN')")
    @Operation(summary = "Update inventory item details")
    public ResponseEntity<ApiResponse<InventoryItemDto>> updateInventoryItem(
            @PathVariable UUID id,
            @Valid @RequestBody CreateInventoryItemRequest request) {
        InventoryItemDto item = inventoryService.updateInventoryItem(id, request);
        return ResponseEntity.ok(ApiResponse.success(item, "Inventory item updated successfully"));
    }

    @GetMapping("/my-inventory")
    @PreAuthorize("hasRole('NGO')")
    @Operation(summary = "Get inventory items managed by logged-in NGO")
    public ResponseEntity<ApiResponse<List<InventoryItemDto>>> getMyInventory(@AuthenticationPrincipal UserDetails userDetails) {
        List<InventoryItemDto> items = inventoryService.getMyInventory(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(items, "NGO inventory retrieved successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all registered relief inventory items across system")
    public ResponseEntity<ApiResponse<List<InventoryItemDto>>> getAllInventory() {
        List<InventoryItemDto> items = inventoryService.getAllInventory();
        return ResponseEntity.ok(ApiResponse.success(items, "Global inventory retrieved successfully"));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get inventory items by category (FOOD, MEDICINE, WATER, etc.)")
    public ResponseEntity<ApiResponse<List<InventoryItemDto>>> getInventoryByCategory(@PathVariable InventoryCategory category) {
        List<InventoryItemDto> items = inventoryService.getInventoryByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(items, "Categorized inventory retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item details by ID")
    public ResponseEntity<ApiResponse<InventoryItemDto>> getInventoryItemById(@PathVariable UUID id) {
        InventoryItemDto item = inventoryService.getInventoryItemById(id);
        return ResponseEntity.ok(ApiResponse.success(item, "Inventory item retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('NGO') or hasRole('ADMIN')")
    @Operation(summary = "Delete an inventory item record")
    public ResponseEntity<ApiResponse<Void>> deleteInventoryItem(@PathVariable UUID id) {
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.ok(ApiResponse.success("Inventory item deleted successfully"));
    }
}