package com.empresa.atendimento.ai.knowledge;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KnowledgeBase {

    private final List<KnowledgeArticle> articles;

    public KnowledgeBase() {
        // TODO: load from database or external source
        this.articles = List.of(
            new KnowledgeArticle("horarios", "Nosso horário de atendimento é de segunda a sexta, das 8h às 18h."),
            new KnowledgeArticle("devolucao", "Para devoluções, o prazo é de 30 dias após a compra com nota fiscal."),
            new KnowledgeArticle("senha", "Para redefinir sua senha, acesse a página de recuperação ou entre em contato conosco.")
        );
    }

    public String search(String query) {
        String lowerQuery = query.toLowerCase();
        StringBuilder result = new StringBuilder();

        for (KnowledgeArticle article : articles) {
            if (lowerQuery.contains(article.topic().toLowerCase())) {
                result.append(article.topic()).append(": ").append(article.content()).append("\n");
            }
        }

        return result.toString().isEmpty() ? "Nenhuma informação encontrada na base de conhecimento." : result.toString();
    }

    public record KnowledgeArticle(String topic, String content) {}
}
