package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Dto.AgentRequestDTO;
import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Agent;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    private Agent convertDtoToEntity(AgentRequestDTO dto) {
        Agent agent = new Agent();
        agent.setAgentName(dto.getAgentName());
        agent.setAgentPhone(dto.getAgentPhone());
        agent.setAgentEmail(dto.getAgentEmail());
        agent.setAgentPhoto(dto.getAgentPhoto());
        agent.setTgreranumber(dto.getTgreranumber());
        return agent;
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(String id) {
        return agentRepository.findById(id);
    }

    public Agent createAgent(AgentRequestDTO agentDto) {
        Agent agent = convertDtoToEntity(agentDto);
        return agentRepository.save(agent);
    }

    public Agent updateAgent(String id, AgentRequestDTO agentDto) {
        Optional<Agent> existingAgentOptional = agentRepository.findById(id);
        if (existingAgentOptional.isPresent()) {
            Agent existingAgent = existingAgentOptional.get();
            existingAgent.setAgentName(agentDto.getAgentName());
            existingAgent.setAgentPhone(agentDto.getAgentPhone());
            existingAgent.setAgentEmail(agentDto.getAgentEmail());
            existingAgent.setAgentPhoto(agentDto.getAgentPhoto());
            existingAgent.setTgreranumber(agentDto.getTgreranumber());
            return agentRepository.save(existingAgent);
        } else {
            throw new RuntimeException("Agent with ID " + id + " not found.");
        }
    }

    public void deleteAgent(String id) {
        agentRepository.deleteById(id);
    }
}