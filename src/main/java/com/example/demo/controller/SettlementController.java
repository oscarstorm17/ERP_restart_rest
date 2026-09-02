package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SettlementDTO;
import com.example.demo.service.SettlementService;

@RestController
@RequestMapping("/settlement")
@CrossOrigin(origins = "http://localhost:5173")
public class SettlementController {

	private SettlementService settlementService;
	public SettlementController(SettlementService settlementService) {
		this.settlementService = settlementService;
	}
	
	@GetMapping("/settlement/{groupID}")
	public ResponseEntity<List<SettlementDTO>> getSettlement(
	        @PathVariable Long groupID) {
	    return ResponseEntity.ok(
	            settlementService.calculateSettlement(groupID)
	    );
	}
	

}
