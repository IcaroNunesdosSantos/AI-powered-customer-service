package com.empresa.atendimento.conversation.domain;

public class ConversationStateMachine {

    private final Conversation conversation;

    public ConversationStateMachine(Conversation conversation) {
        this.conversation = conversation;
    }

    public void transitionTo(ConversationStatus targetStatus) {
        ConversationStatus currentStatus = conversation.getStatus();
        if (!isValidTransition(currentStatus, targetStatus)) {
            throw new IllegalStateException(
                "Invalid transition from " + currentStatus + " to " + targetStatus
            );
        }
        conversation.setStatus(targetStatus);
    }

    private boolean isValidTransition(ConversationStatus from, ConversationStatus to) {
        return switch (from) {
            case OPEN -> to == ConversationStatus.AI_HANDLING || to == ConversationStatus.AGENT_ASSIGNED;
            case AI_HANDLING -> to == ConversationStatus.ESCALATED || to == ConversationStatus.RESOLVED;
            case AGENT_ASSIGNED -> to == ConversationStatus.ESCALATED || to == ConversationStatus.RESOLVED;
            case ESCALATED -> to == ConversationStatus.AGENT_ASSIGNED || to == ConversationStatus.RESOLVED;
            case RESOLVED -> to == ConversationStatus.CLOSED;
            case CLOSED -> false;
        };
    }
}
