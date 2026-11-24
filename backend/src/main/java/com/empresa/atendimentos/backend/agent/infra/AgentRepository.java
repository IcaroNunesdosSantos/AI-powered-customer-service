package com.empresa.atendimento.agent.infra;

import com.empresa.atendimento.agent.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {
}
