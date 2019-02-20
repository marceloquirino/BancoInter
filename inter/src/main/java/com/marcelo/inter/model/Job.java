package com.marcelo.inter.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Job {

	@Id
	//@GeneratedValue(strategy= GenerationType.IDENTITY)
	
	@Column(nullable=false)
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private boolean active;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Task> taskList = new ArrayList<Task>();
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "parent_job_id", referencedColumnName = "id", nullable = true)
	private Job parentJob;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	public Job getParentJob() {
		return parentJob;
	}
	public void setParentJob(Job parentJob) {
		this.parentJob = parentJob;
	}

	public boolean validateParent(Integer id, String name, Job parent) {
		if(parent.getId() == id || parent.getName().equals(name))
			return false;
		else if(parent.getParentJob() != null) {
			return validateParent(id, name, parent.getParentJob());
		}
		return true;
	}
}
