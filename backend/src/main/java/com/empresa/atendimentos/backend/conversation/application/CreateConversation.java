package com.empresa.atendimento.conversation.application;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.infra.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateConversation {

    private final ConversationRepository conversationRepository;

    public CreateConversation(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation execute(String customerId) {
        Conversation conversation = new Conversation(customerId);
        return conversationRepository.save(conversation);
    }
}
