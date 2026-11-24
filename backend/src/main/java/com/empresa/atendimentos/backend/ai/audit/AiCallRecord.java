package com.empresa.atendimento.ai.audit;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ai_call_records")
public class AiCallRecord {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Column(nullable = false)
    private String conversationId;

    @Column(columnDefinition = "TEXT")
    private String input;

    @Column(columnDefinition = "TEXT")
    private String output;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private LocalDateTime calledAt;

    public AiCallRecord() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public LocalDateTime getCalledAt() { return calledAt; }
    public void setCalledAt(LocalDateTime calledAt) { this.calledAt = calledAt; }
}
