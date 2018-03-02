package com.sims.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.system.dao.RightDao;
import com.sims.system.entity.Right;

@Service
@Transactional
public class RightService implements IRightService {
	@Autowired
	private RightDao rightDao;

	@Override
	public void saveOrUpdate(Right right) {
		// TODO Auto-generated method stub
		rightDao.save(right);
	}

	@Override
	public void delete(Right right) {
		// TODO Auto-generated method stub
		rightDao.delete(right);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		Iterable<Right> all = rightDao.findAll(ids);
		if (all != null) {
			for(Right r : all) {
				r.setRoles(null);
				rightDao.delete(r);
			}
			
		}
	}

	@Override
	public Page<Right> findAll(Specification<Right> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return rightDao.findAll(spec, pageable);
	}

	@Override
	public Right findOne(Long id) {
		// TODO Auto-generated method stub
		return rightDao.findOne(id);
	}

	@Override
	public Set<Right> findRightByRoleId(Long id) {
		// TODO Auto-generated method stub
		return rightDao.findRightByRoleId(id);
	}

	@Override
	public List<Right> findAll() {
		// TODO Auto-generated method stub
		return (List<Right>) rightDao.findAll();
	}

}
