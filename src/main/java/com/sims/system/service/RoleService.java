package com.sims.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.system.dao.RoleDao;
import com.sims.system.entity.Role;

@Service
@Transactional
public class RoleService implements IRoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public void saveOrUpdate(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	@Override
	public void delete(Role role) {
		// TODO Auto-generated method stub
		roleDao.delete(role);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		Iterable<Role> all = roleDao.findAll(ids);
		if (all != null) {
			for(Role r : all) {
				r.setRights(null);
				r.setUsers(null);
				roleDao.delete(r);
			}
			
		}
	}

	@Override
	public Page<Role> findAll(Specification<Role> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return roleDao.findAll(spec, pageable);
	}

	@Override
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findOne(id);
	}

	@Override
	public Role findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleDao.findByRoleName(roleName);
	}

}
