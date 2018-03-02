package com.sims.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.system.entity.Role;

public interface IRoleService {
	public void saveOrUpdate(Role role);

	public void delete(Role role);

	public void delete(List<Long> ids);

	public Page<Role> findAll(Specification<Role> spec, Pageable pageable);

	public Role findOne(Long id);
	
	public Role findByRoleName(String roleName);

}
