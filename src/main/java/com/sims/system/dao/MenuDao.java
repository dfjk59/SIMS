package com.sims.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sims.system.entity.Menu;

public interface MenuDao extends CrudRepository<Menu, Long> {

	@Query("from Menu m where m.parentNode.id = null")
	public List<Menu> findParentNodes();

	@Query("from Menu m where m.parentNode.id = ?1")
	public List<Menu> findChildNodes(Long parentId);

}
