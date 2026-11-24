package com.empresa.atendimento.agent.api.dto;

import com.empresa.atendimento.agent.domain.Agent;
import com.empresa.atendimento.agent.domain.AgentStatus;

import java.util.UUID;

public record AgentResponse(
    UUID id,
    String email,
    String name,
    AgentStatus status,
    String specialization
) {
    public static AgentResponse from(Agent agent) {
        return new AgentResponse(
            agent.getId(),
            agent.getEmail(),
            agent.getName(),
            agent.getStatus(),
            agent.getSpecialization()
        );
    }
}
