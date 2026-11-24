package com.empresa.atendimento.escalation.policy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DefaultEscalationPolicy implements EscalationPolicy {

    private static final Set<String> ESCALATION_KEYWORDS = Set.of(
        "reclamação", "reclamacao", "gerente", "juiz", "procon", "processo",
        "cancelar", "devolução", "devolucao", "indenização", "indenizacao"
    );

    @Override
    public boolean shouldEscalate(String conversationId, String message) {
        String lowerMessage = message.toLowerCase();
        return ESCALATION_KEYWORDS.stream().anyMatch(lowerMessage::contains);
    }

    @Override
    public String getEscalationReason(String conversationId, String message) {
        String lowerMessage = message.toLowerCase();
        for (String keyword : ESCALATION_KEYWORDS) {
            if (lowerMessage.contains(keyword)) {
                return "Palavra-chave detectada: " + keyword;
            }
        }
        return "Critério de escalonamento não especificado";
    }
}
