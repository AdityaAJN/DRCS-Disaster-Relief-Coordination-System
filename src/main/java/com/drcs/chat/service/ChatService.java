package com.drcs.chat.service;

import com.drcs.chat.dto.ChatMessageDto;
import com.drcs.chat.dto.SendChatMessageRequest;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatMessageDto sendMessage(SendChatMessageRequest request, String senderEmail);

    List<ChatMessageDto> getConversation(UUID otherUserId, String userEmail);

    List<ChatMessageDto> getHelpRequestChat(UUID helpRequestId);
}
