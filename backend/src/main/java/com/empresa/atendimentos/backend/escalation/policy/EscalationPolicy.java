package com.empresa.atendimento.escalation.policy;

public interface EscalationPolicy {
    boolean shouldEscalate(String conversationId, String message);
    String getEscalationReason(String conversationId, String message);
}
