package com.cognizant.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cognizant.dto.PolicyDto;
import com.cognizant.entities.Policy;
import com.cognizant.repository.PolicyRepo;
import com.cognizant.services.PolicyService;
import com.cognizant.utilities.PolicyIdGenerator;

import jakarta.transaction.Transactional;

@Service
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepo policyRepo;
    private final ModelMapper modelMapper;
 
    public PolicyServiceImpl(PolicyRepo policyRepo, ModelMapper modelMapper) {
        this.policyRepo = policyRepo;
        this.modelMapper = modelMapper;
    }

	@Override
	@Transactional
	public PolicyDto insertPolicy(PolicyDto policyDto) {
        String policyNo = PolicyIdGenerator.generatePolicyId(policyDto.getInsuredLastName(), policyDto.getVehicleNo(), LocalDate.now());
 
        policyDto.setPolicyNo(policyNo);
        Policy policy = modelMapper.map(policyDto, Policy.class);
        policy = policyRepo.save(policy);
 
        return modelMapper.map(policy, PolicyDto.class);
	}

	@Override
	@Transactional
	public List<PolicyDto> getAllPolicies() {
		List<Policy> policies=this.policyRepo.findAll();
		 List<PolicyDto> policyDtos = new ArrayList<>();
	        for (Policy policy :policies) {
	            policyDtos.add(modelMapper.map(policy, PolicyDto.class));
	        }
	        return policyDtos;
	}

}
