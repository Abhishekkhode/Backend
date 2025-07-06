package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Dto.AgentRequestDTO;
import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Agent;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    // ✅ GET all agents
    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.getAllAgents();
        return ResponseEntity.ok(agents);
    }

    // ✅ GET agent by ID
    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable String id) {
        return agentService.getAgentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE agent
//    @PostMapping
//    public ResponseEntity<Agent> createAgent(@Valid @RequestBody AgentRequestDTO agentDto) {
//        Agent createdAgent = agentService.createAgent(agentDto);
//        return ResponseEntity.status(201).body(createdAgent);
//    }
    @PostMapping
    public ResponseEntity<?> createAgent(@Valid @RequestBody AgentRequestDTO agentDto, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error ->
                    System.out.println("Validation error in field '" + error.getField() + "': " + error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        Agent createdAgent = agentService.createAgent(agentDto);
        return ResponseEntity.status(201).body(createdAgent);
    }



    // ✅ UPDATE agent
    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable String id,
                                             @Valid @RequestBody AgentRequestDTO agentDto) {
        Optional<Agent> existing = agentService.getAgentById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Agent updatedAgent = agentService.updateAgent(id, agentDto);
        return ResponseEntity.ok(updatedAgent);
    }

    // ✅ DELETE agent
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable String id) {
        Optional<Agent> existing = agentService.getAgentById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}
