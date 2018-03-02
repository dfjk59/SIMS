package com.sims.system.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sims.system.entity.Right;

public interface RightDao extends PagingAndSortingRepository<Right, Long>, JpaSpecificationExecutor<Right> {
	@Query("SELECT r FROM Right r JOIN r.roles role WHERE role.id = ?1")
	public Set<Right> findRightByRoleId(Long id);
}
