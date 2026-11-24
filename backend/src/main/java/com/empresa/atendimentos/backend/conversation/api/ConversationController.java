package com.empresa.atendimento.conversation.api;

import com.empresa.atendimento.conversation.api.dto.ConversationResponse;
import com.empresa.atendimento.conversation.api.dto.CreateConversationRequest;
import com.empresa.atendimento.conversation.api.dto.SendMessageRequest;
import com.empresa.atendimento.conversation.application.ClaimConversation;
import com.empresa.atendimento.conversation.application.CreateConversation;
import com.empresa.atendimento.conversation.application.ReceiveCustomerMessage;
import com.empresa.atendimento.conversation.domain.Conversation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final CreateConversation createConversation;
    private final ReceiveCustomerMessage receiveCustomerMessage;
    private final ClaimConversation claimConversation;

    public ConversationController(CreateConversation createConversation,
                                  ReceiveCustomerMessage receiveCustomerMessage,
                                  ClaimConversation claimConversation) {
        this.createConversation = createConversation;
        this.receiveCustomerMessage = receiveCustomerMessage;
        this.claimConversation = claimConversation;
    }

    @PostMapping
    public ResponseEntity<ConversationResponse> create(@RequestBody CreateConversationRequest request) {
        Conversation conversation = createConversation.execute(request.customerId());
        return ResponseEntity.ok(ConversationResponse.from(conversation));
    }

    @PostMapping("/message")
    public ResponseEntity<ConversationResponse> sendMessage(@RequestBody SendMessageRequest request) {
        Conversation conversation = receiveCustomerMessage.execute(request.conversationId(), request.content());
        return ResponseEntity.ok(ConversationResponse.from(conversation));
    }

    @PostMapping("/{conversationId}/claim")
    public ResponseEntity<ConversationResponse> claim(
            @PathVariable UUID conversationId,
            @RequestParam String agentId) {
        Conversation conversation = claimConversation.execute(conversationId, agentId);
        return ResponseEntity.ok(ConversationResponse.from(conversation));
    }
}
