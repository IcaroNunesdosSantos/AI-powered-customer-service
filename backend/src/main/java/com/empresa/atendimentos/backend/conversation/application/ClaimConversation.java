package com.empresa.atendimento.conversation.application;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.domain.ConversationStateMachine;
import com.empresa.atendimento.conversation.infra.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClaimConversation {

    private final ConversationRepository conversationRepository;

    public ClaimConversation(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public Conversation execute(UUID conversationId, String agentId) {
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        conversation.setAgentId(agentId);
        ConversationStateMachine stateMachine = new ConversationStateMachine(conversation);
        stateMachine.transitionTo(com.empresa.atendimento.conversation.domain.ConversationStatus.AGENT_ASSIGNED);

        return conversationRepository.save(conversation);
    }
}
