package com.example.demo.service;

import java.util.Optional;

import com.example.demo.dto.DataDto;
import com.example.demo.model.BoData;

public interface DataService {
	
	BoData saveData(Long id, DataDto dto, String option);
	
	String getDifferences(Long id);
	
	Optional<BoData> get(Long id);

	
}
