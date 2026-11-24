package com.empresa.atendimento.agent.application;

import com.empresa.atendimento.agent.domain.Agent;
import com.empresa.atendimento.agent.infra.AgentRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterAgent {

    private final AgentRepository agentRepository;

    public RegisterAgent(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Agent execute(String email, String name) {
        Agent agent = new Agent(email, name);
        return agentRepository.save(agent);
    }
}
