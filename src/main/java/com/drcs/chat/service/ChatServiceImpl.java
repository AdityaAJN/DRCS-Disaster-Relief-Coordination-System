package com.drcs.chat.service;

import com.drcs.chat.ChatMessage;
import com.drcs.chat.ChatMessageRepository;
import com.drcs.chat.dto.ChatMessageDto;
import com.drcs.chat.dto.SendChatMessageRequest;
import com.drcs.exception.ResourceNotFoundException;
import com.drcs.helprequest.HelpRequest;
import com.drcs.helprequest.HelpRequestRepository;
import com.drcs.user.User;
import com.drcs.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public ChatMessageDto sendMessage(SendChatMessageRequest request, String senderEmail) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Sender user not found: " + senderEmail));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver user not found: " + request.getReceiverId()));

        HelpRequest helpRequest = null;
        if (request.getHelpRequestId() != null) {
            helpRequest = helpRequestRepository.findById(request.getHelpRequestId()).orElse(null);
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .helpRequest(helpRequest)
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        ChatMessage saved = chatMessageRepository.save(chatMessage);

        ChatMessageDto dto = ChatMessageDto.builder()
                .id(saved.getId())
                .senderId(sender.getId())
                .senderName(sender.getFullName())
                .receiverId(receiver.getId())
                .receiverName(receiver.getFullName())
                .helpRequestId(helpRequest != null ? helpRequest.getId() : null)
                .message(saved.getMessage())
                .sentAt(saved.getSentAt())
                .build();

        messagingTemplate.convertAndSendToUser(receiver.getEmail(), "/queue/messages", dto);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> getConversation(UUID otherUserId, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Other user not found: " + otherUserId));

        return chatMessageRepository.findConversationBetweenUsers(currentUser, otherUser).stream()
                .map(msg -> ChatMessageDto.builder()
                        .id(msg.getId())
                        .senderId(msg.getSender().getId())
                        .senderName(msg.getSender().getFullName())
                        .receiverId(msg.getReceiver().getId())
                        .receiverName(msg.getReceiver().getFullName())
                        .helpRequestId(msg.getHelpRequest() != null ? msg.getHelpRequest().getId() : null)
                        .message(msg.getMessage())
                        .sentAt(msg.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDto> getHelpRequestChat(UUID helpRequestId) {
        HelpRequest helpRequest = helpRequestRepository.findById(helpRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Help request not found: " + helpRequestId));

        return chatMessageRepository.findByHelpRequestOrderBySentAtAsc(helpRequest).stream()
                .map(msg -> ChatMessageDto.builder()
                        .id(msg.getId())
                        .senderId(msg.getSender().getId())
                        .senderName(msg.getSender().getFullName())
                        .receiverId(msg.getReceiver().getId())
                        .receiverName(msg.getReceiver().getFullName())
                        .helpRequestId(helpRequest.getId())
                        .message(msg.getMessage())
                        .sentAt(msg.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }
}

