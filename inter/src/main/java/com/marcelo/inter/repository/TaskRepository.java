package com.marcelo.inter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.inter.model.*;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
