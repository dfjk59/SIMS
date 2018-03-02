package com.sims.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.system.dao.RoleDao;
import com.sims.system.dao.UserDao;
import com.sims.system.entity.Role;
import com.sims.system.entity.User;

@Service
@Transactional
public class UserService implements IUserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void saveOrUpdate(User user, Long id) {
		// TODO Auto-generated method stub
		if(id!=null) {
			Role role = roleDao.findOne(id);
			System.out.println(111);
			user.setRole(role);
			userDao.save(user);
		}else {
			userDao.save(user);
		}
		
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		Iterable<User> all = userDao.findAll(ids);
		
		for(User u : all) {
			u.setRole(null);
			userDao.delete(u);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Specification<User> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return userDao.findAll(spec, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}

	@Override
	public User findByUserName(String name) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(name);
	}

}
