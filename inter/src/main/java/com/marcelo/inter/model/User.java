package com.marcelo.inter.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	
	@Column(nullable=false)
	private Integer id;
	
	@Column(nullable=false, length=2048)
	private String name;

	@Column(nullable=false, length=2048)
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<SingleUnit> singleUnitList = new ArrayList<SingleUnit>();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SingleUnit> getSingleUnitList() {
		return singleUnitList;
	}

	public void setSingleUnitList(List<SingleUnit> singleUnitList) {
		this.singleUnitList = singleUnitList;
	}
}
