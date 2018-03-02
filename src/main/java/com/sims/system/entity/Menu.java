package com.sims.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
 * 菜单字段
 * */
@Entity
@Table(name = "t_menu")
public class Menu {
	private Long id;
	private String menuName;
	private String iconCls;
	private String viewType;
	private String perm;//权限
	private Menu parentNode;
	private List<Menu> childNodes = new ArrayList<Menu>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getMenuName() {
		return menuName;
	}

	public String getIconCls() {
		return iconCls;
	}

	public String getViewType() {
		return viewType;
	}

	public String getPerm() {
		return perm;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	public Menu getParentNode() {
		return parentNode;
	}

	@OneToMany(mappedBy="parentNode", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<Menu> getChildNodes() {
		return childNodes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public void setParentNode(Menu parentNode) {
		this.parentNode = parentNode;
	}

	public void setChildNodes(List<Menu> childNodes) {
		this.childNodes = childNodes;
	}

}
