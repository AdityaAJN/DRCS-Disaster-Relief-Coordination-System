package com.drcs.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendChatMessageRequest {

    @NotNull(message = "Receiver ID is required")
    private UUID receiverId;

    private UUID helpRequestId;

    @NotBlank(message = "Message text is required")
    private String message;
}