package com.sims.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
/*
 * 角色字段
 * */
@Entity
@Table(name = "t_role")
public class Role {
	private Long id;
	private String roleName;
	private String description;
	private List<User> users = new ArrayList<User>();
	private List<Right> rights = new ArrayList<Right>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getDescription() {
		return description;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	public List<User> getUsers() {
		return users;
	}

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Right> getRights() {
		return rights;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", description=" + description + "]";
	}

}
