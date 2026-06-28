package com.drcs.chat.controller;

import com.drcs.chat.dto.ChatMessageDto;
import com.drcs.chat.dto.SendChatMessageRequest;
import com.drcs.chat.service.ChatService;
import com.drcs.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Emergency Chat API", description = "Endpoints for real-time STOMP messaging and chat history retrieval")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    @Operation(summary = "Send a chat message via REST API")
    public ResponseEntity<ApiResponse<ChatMessageDto>> sendMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SendChatMessageRequest request) {
        ChatMessageDto dto = chatService.sendMessage(request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(dto, "Message sent successfully"));
    }

    @MessageMapping("/chat.send")
    public void sendWebSocketMessage(@Payload SendChatMessageRequest request, Principal principal) {
        chatService.sendMessage(request, principal.getName());
    }

    @GetMapping("/conversation/{userId}")
    @Operation(summary = "Get private conversation history between logged-in user and another user")
    public ResponseEntity<ApiResponse<List<ChatMessageDto>>> getConversation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID userId) {
        List<ChatMessageDto> messages = chatService.getConversation(userId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(messages, "Conversation history retrieved successfully"));
    }

    @GetMapping("/request/{helpRequestId}")
    @Operation(summary = "Get chat history linked specifically to a help request dispatch")
    public ResponseEntity<ApiResponse<List<ChatMessageDto>>> getHelpRequestChat(@PathVariable UUID helpRequestId) {
        List<ChatMessageDto> messages = chatService.getHelpRequestChat(helpRequestId);
        return ResponseEntity.ok(ApiResponse.success(messages, "Request chat history retrieved successfully"));
    }
}