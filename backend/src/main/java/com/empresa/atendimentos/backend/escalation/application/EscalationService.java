package com.empresa.atendimento.escalation.application;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.domain.ConversationStateMachine;
import com.empresa.atendimento.conversation.infra.ConversationRepository;
import com.empresa.atendimento.escalation.policy.EscalationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EscalationService {

    private static final Logger log = LoggerFactory.getLogger(EscalationService.class);

    private final EscalationPolicy escalationPolicy;
    private final ConversationRepository conversationRepository;

    public EscalationService(EscalationPolicy escalationPolicy,
                             ConversationRepository conversationRepository) {
        this.escalationPolicy = escalationPolicy;
        this.conversationRepository = conversationRepository;
    }

    public boolean checkAndEscalate(UUID conversationId, String message) {
        if (!escalationPolicy.shouldEscalate(conversationId.toString(), message)) {
            return false;
        }

        String reason = escalationPolicy.getEscalationReason(conversationId.toString(), message);
        log.info("Escalating conversation {}: {}", conversationId, reason);

        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        ConversationStateMachine stateMachine = new ConversationStateMachine(conversation);
        stateMachine.transitionTo(com.empresa.atendimento.conversation.domain.ConversationStatus.ESCALATED);
        conversation.setContext(reason);
        conversationRepository.save(conversation);

        return true;
    }
}
