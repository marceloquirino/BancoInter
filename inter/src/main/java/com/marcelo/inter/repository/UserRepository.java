package com.marcelo.inter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.inter.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
