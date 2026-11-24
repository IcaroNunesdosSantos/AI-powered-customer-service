package com.empresa.atendimento.ai.provider;

import com.empresa.atendimento.ai.port.AiProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpAiProvider implements AiProvider {

    @Value("${ai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${ai.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateResponse(String prompt, String context) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> body = Map.of(
            "model", "gpt-4",
            "messages", new Object[]{
                Map.of("role", "system", "content", context),
                Map.of("role", "user", "content", prompt)
            }
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        // TODO: parse response properly
        return restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class).getBody();
    }

    @Override
    public boolean isAvailable() {
        return apiKey != null && !apiKey.isBlank();
    }
}
