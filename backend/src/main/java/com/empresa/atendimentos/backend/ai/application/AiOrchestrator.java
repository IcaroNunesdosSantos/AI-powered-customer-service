package com.empresa.atendimento.ai.application;

import com.empresa.atendimento.ai.audit.AiCallRecord;
import com.empresa.atendimento.ai.audit.AiCallRepository;
import com.empresa.atendimento.ai.knowledge.KnowledgeBase;
import com.empresa.atendimento.ai.port.AiProvider;
import com.empresa.atendimento.ai.prompt.PromptTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(AiOrchestrator.class);

    private final List<AiProvider> aiProviders;
    private final ContextBuilder contextBuilder;
    private final KnowledgeBase knowledgeBase;
    private final AiCallRepository aiCallRepository;

    public AiOrchestrator(List<AiProvider> aiProviders,
                          ContextBuilder contextBuilder,
                          KnowledgeBase knowledgeBase,
                          AiCallRepository aiCallRepository) {
        this.aiProviders = aiProviders;
        this.contextBuilder = contextBuilder;
        this.knowledgeBase = knowledgeBase;
        this.aiCallRepository = aiCallRepository;
    }

    public String processCustomerMessage(String conversationId, String customerMessage) {
        // Build context from conversation history and knowledge base
        String context = contextBuilder.build(conversationId);
        String knowledge = knowledgeBase.search(customerMessage);
        String fullContext = context + "\n\nKnowledge base:\n" + knowledge;

        // Select prompt template
        String prompt = PromptTemplate.customerResponse(customerMessage);

        // Try each provider until one succeeds
        for (AiProvider provider : aiProviders) {
            if (!provider.isAvailable()) {
                log.warn("AI provider not available, skipping");
                continue;
            }
            try {
                String response = provider.generateResponse(prompt, fullContext);
                logAiCall(conversationId, customerMessage, response, provider.getClass().getSimpleName(), true);
                return response;
            } catch (Exception e) {
                log.error("AI provider failed: {}", provider.getClass().getSimpleName(), e);
                logAiCall(conversationId, customerMessage, null, provider.getClass().getSimpleName(), false);
            }
        }

        throw new RuntimeException("All AI providers are unavailable");
    }

    private void logAiCall(String conversationId, String input, String output, String provider, boolean success) {
        AiCallRecord record = new AiCallRecord();
        record.setConversationId(conversationId);
        record.setInput(input);
        record.setOutput(output);
        record.setProvider(provider);
        record.setSuccess(success);
        record.setCalledAt(LocalDateTime.now());
        aiCallRepository.save(record);
    }
}
