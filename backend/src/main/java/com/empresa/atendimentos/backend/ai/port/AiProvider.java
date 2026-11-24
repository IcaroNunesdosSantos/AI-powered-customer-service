package com.empresa.atendimento.ai.port;

public interface AiProvider {
    String generateResponse(String prompt, String context);
    boolean isAvailable();
}
