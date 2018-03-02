package com.sims.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
/*
 * 权限字段
 * */
@Entity
@Table(name = "t_right")
public class Right {
	private Long Id;
	private String rightName;
	private String description;
	private List<Role> roles = new ArrayList<Role>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return Id;
	}

	public String getRightName() {
		return rightName;
	}

	public String getDescription() {
		return description;
	}
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL,mappedBy="rights")
	public List<Role> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Right [Id=" + Id + ", rightName=" + rightName + ", description=" + description + "]";
	}
	
	

}
