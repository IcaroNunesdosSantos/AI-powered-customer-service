package com.empresa.atendimento.ai.provider;

import com.empresa.atendimento.ai.port.AiProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class SpringAiProvider implements AiProvider {

    private final ChatClient chatClient;

    public SpringAiProvider(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generateResponse(String prompt, String context) {
        return chatClient.prompt()
            .system(context)
            .user(prompt)
            .call()
            .content();
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
