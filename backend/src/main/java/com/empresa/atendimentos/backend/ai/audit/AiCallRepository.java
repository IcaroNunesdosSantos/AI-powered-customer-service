package com.empresa.atendimento.ai.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AiCallRepository extends JpaRepository<AiCallRecord, UUID> {
}
