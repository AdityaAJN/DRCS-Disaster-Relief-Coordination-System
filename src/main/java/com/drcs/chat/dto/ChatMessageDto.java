package com.drcs.chat.dto;

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
public class ChatMessageDto {

    private UUID id;
    private UUID senderId;
    private String senderName;
    private UUID receiverId;
    private String receiverName;
    private UUID helpRequestId;
    private String message;
    private LocalDateTime sentAt;
}