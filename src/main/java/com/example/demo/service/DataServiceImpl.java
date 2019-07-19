package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.DataDto;
import com.example.demo.model.BoData;
import com.example.demo.repo.DataRepo;

@Service
public class DataServiceImpl implements DataService {
	
	@Autowired
	private DataRepo dataRepo;

	@Override
	public BoData saveData(Long id, DataDto dto, String option) {
		
		final String LEFT = "left";
		
		Optional<BoData> data = get(id);
		BoData boData = null;
		
		if (data.isPresent()) {
			boData = data.get();			
			if(option.equals(LEFT)) {
				boData.setLeft(dto.getBinData());
			} else {
				boData.setRight(dto.getBinData());
			}			
		} else {
			boData = new BoData();
			boData.setId(id);   
	        if (option.equals(LEFT)) {
	        	boData.setLeft(dto.getBinData());
	        } else {
	        	boData.setRight(dto.getBinData());
	        }
		}
	    
        return dataRepo.save(boData);
    }
	
	

	@Override
	public Optional<BoData> get(Long id) {
		
		return dataRepo.findById(id);
	}

	@Override
	public String getDifferences(Long id) {
		
		 Optional<BoData> data = get(id);
		 
		 if (!data.isPresent()) {
			 return "ERROR: Can not find data with ID: " + id;
		 }
		 
		 if (data.get().getLeft() == null) {
			 return "ERROR: Left data with ID: " + id + " don't exist";
		 }
		
		 if (data.get().getRight() == null) {
			 return "ERROR: Right data with ID: " + id + " don't exist";
		 }
		 
		 byte[] left = data.get().getLeft().getBytes();
	     byte[] right = data.get().getRight().getBytes();
	     List<Integer> diffPosition = new ArrayList<>();
	     
	     if (left.length != right.length) {
	            return "Data not equal";
         }
	     
	     for( int index = 0; index < left.length; ++index ) {
	         if( left[index] != right[index]) {
	        	 diffPosition.add(index);
	         }
	     }
	     
	     if (diffPosition.isEmpty()) {	    	 
	            return "Data equal";
	            
	     } else {
	    	 
	    	 StringBuilder out = new StringBuilder();
	    	 out.append("Equal data size but diff are in possitions: ");
		    
		     for (Object o : diffPosition)
		     {
		       out.append(o.toString());
		       out.append(",");
		     }
		     
		     return out.toString(); 
	     }  
	}
}
