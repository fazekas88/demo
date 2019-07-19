package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DataDto;
import com.example.demo.model.BoData;
import com.example.demo.service.DataService;

@RestController
@RequestMapping("/v1/diff/{id}")
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	final String LEFT = "left";
	final String RIGHT = "right";
	
	@PostMapping("/left")
	public ResponseEntity<String> doLeft(@PathVariable Long id, @RequestBody DataDto data) {
		
		BoData boData = dataService.saveData(id, data, LEFT);
		String message = new String("Saved LEFT data with id:" +boData.getId());
	
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@PostMapping("/right")
	public ResponseEntity<String> doRight(@PathVariable Long id, @RequestBody DataDto data) {
		
		BoData boData = dataService.saveData(id, data, RIGHT);
		String message = new String("Created RIGHT data with id:" +boData.getId());
	
		return new ResponseEntity<>(message , HttpStatus.CREATED);
	}
	
	@GetMapping()
    public ResponseEntity<?> getDiff(@PathVariable Long id) {

        String message = dataService.getDifferences(id);

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
	
	

}
