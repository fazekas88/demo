package com.example.demo.repo;

import org.springframework.stereotype.Repository;

import com.example.demo.model.BoData;

import org.springframework.data.repository.CrudRepository;


@Repository
public interface DataRepo extends CrudRepository<BoData, Long> {

}
