package com.sims.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.system.entity.Right;

public interface IRightService {
	public void saveOrUpdate(Right right);

	public void delete(Right right);

	public void delete(List<Long> ids);

	public Page<Right> findAll(Specification<Right> spec, Pageable pageable);

	public Right findOne(Long id);

	public Set<Right> findRightByRoleId(Long id);
	
	public List<Right> findAll();
}
