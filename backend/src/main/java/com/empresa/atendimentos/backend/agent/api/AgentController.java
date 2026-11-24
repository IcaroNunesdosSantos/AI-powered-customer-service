package com.empresa.atendimento.agent.api;

import com.empresa.atendimento.agent.api.dto.AgentResponse;
import com.empresa.atendimento.agent.api.dto.RegisterAgentRequest;
import com.empresa.atendimento.agent.application.RegisterAgent;
import com.empresa.atendimento.agent.domain.Agent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final RegisterAgent registerAgent;

    public AgentController(RegisterAgent registerAgent) {
        this.registerAgent = registerAgent;
    }

    @PostMapping
    public ResponseEntity<AgentResponse> register(@RequestBody RegisterAgentRequest request) {
        Agent agent = registerAgent.execute(request.email(), request.name());
        return ResponseEntity.ok(AgentResponse.from(agent));
    }
}
