package com.empresa.atendimento.conversation.application;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.domain.ConversationMessage;
import com.empresa.atendimento.conversation.domain.ConversationStateMachine;
import com.empresa.atendimento.conversation.domain.MessageRole;
import com.empresa.atendimento.conversation.infra.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReceiveCustomerMessage {

    private final ConversationRepository conversationRepository;

    public ReceiveCustomerMessage(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public Conversation execute(UUID conversationId, String messageContent) {
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        ConversationMessage message = new ConversationMessage(conversation, MessageRole.CUSTOMER, messageContent);
        conversation.getMessages().add(message);

        ConversationStateMachine stateMachine = new ConversationStateMachine(conversation);
        if (conversation.getStatus() == com.empresa.atendimento.conversation.domain.ConversationStatus.OPEN) {
            stateMachine.transitionTo(com.empresa.atendimento.conversation.domain.ConversationStatus.AI_HANDLING);
        }

        return conversationRepository.save(conversation);
    }
}
