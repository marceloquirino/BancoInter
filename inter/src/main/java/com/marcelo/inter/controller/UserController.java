package com.marcelo.inter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.inter.model.SingleUnit;
import com.marcelo.inter.model.User;
import com.marcelo.inter.repository.UserRepository;
import com.marcelo.inter.util.RSACryptography;
import com.marcelo.inter.util.Util;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserRepository repo;

	@GetMapping()
	public ResponseEntity<?> getList() {
		List<User> userList = repo.findAll();
		if(userList.isEmpty()) {
			return new ResponseEntity<>("There is no user registered", HttpStatus.OK);
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	  
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
		Optional<User> userOpt = repo.findById(id);
		if(userOpt.isPresent()) {
			return new ResponseEntity<>(userOpt, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody User payload){
		if(Util.IsNullOrEmpty(payload.getName())) {
			return new ResponseEntity<>("The name is required", HttpStatus.FORBIDDEN);
		}
		if(Util.IsNullOrEmpty(payload.getEmail())) {
			return new ResponseEntity<>("The email is required", HttpStatus.FORBIDDEN);
		}
		for (SingleUnit singleUnit : payload.getSingleUnitList()) {
			if(Util.IsNullOrEmpty(singleUnit.getStringNumber())) {
				return new ResponseEntity<>("The stringNumber of singleUnit is required", HttpStatus.FORBIDDEN);
			}
			if(!Util.IsBiggerThen(0, singleUnit.getNumberTimes())) {
				return new ResponseEntity<>("The numberTimes of singleUnit is required and bigger then 0", HttpStatus.FORBIDDEN);
			}
			singleUnit.setResultByCalculation();
		}
		User createdData = repo.save(payload);
		return new ResponseEntity<>(createdData, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody User payload){
		if(!repo.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(Util.IsNullOrEmpty(payload.getEmail())) {
			return new ResponseEntity<>("The email is required", HttpStatus.FORBIDDEN);
		}
		for (SingleUnit singleUnit : payload.getSingleUnitList()) {
			if(Util.IsNullOrEmpty(singleUnit.getStringNumber())) {
				return new ResponseEntity<>("The stringNumber of singleUnit is required", HttpStatus.FORBIDDEN);
			}
			if(!Util.IsBiggerThen(0, singleUnit.getNumberTimes())) {
				return new ResponseEntity<>("The numberTimes of singleUnit is required and bigger then 0", HttpStatus.FORBIDDEN);
			}
			singleUnit.setResultByCalculation();
		}
		if(payload.getId() == null) {
			payload.setId(id);
		}

		User createdData = repo.save(payload);
		return new ResponseEntity<>(createdData, HttpStatus.OK);
	}
	
	@PutMapping(value = "/encrypt/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @RequestBody String pKey){
		Optional<User> userOpt = repo.findById(id);
		if(!userOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		userOpt.ifPresent(user ->{
			try {
			user.setEmail(RSACryptography.encrypt(user.getEmail(), pKey));
			user.setName(RSACryptography.encrypt(user.getName(), pKey));
			repo.save(user);
			}catch(Exception e) {
				int a =1;
			}
		}); 
		return new ResponseEntity<>(userOpt, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		if(!repo.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.deleteById(id);
		return new ResponseEntity<>("User of id="+id+" was deleted", HttpStatus.OK);
	}
}
