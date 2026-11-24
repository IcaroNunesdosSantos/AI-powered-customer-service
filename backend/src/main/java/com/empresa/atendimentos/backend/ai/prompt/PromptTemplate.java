package com.empresa.atendimento.ai.prompt;

public final class PromptTemplate {

    private static final String SYSTEM_PROMPT = """
        Você é um assistente de atendimento ao cliente inteligente e empático.
        Responda de forma clara, concisa e educada.
        Se não souber a resposta, diga que vai transferir para um agente humano.
        Mantenha o tom profissional e amigável.
        Versão: 1.0
        """;

    private PromptTemplate() {}

    public static String customerResponse(String customerMessage) {
        return "Cliente disse: %s\n\nForneça uma resposta adequada.".formatted(customerMessage);
    }

    public static String systemPrompt() {
        return SYSTEM_PROMPT;
    }

    public static String escalationPrompt(String reason) {
        return "A conversa precisa ser escalada. Motivo: %s\n\nForneça uma mensagem de transferência educada.".formatted(reason);
    }
}
