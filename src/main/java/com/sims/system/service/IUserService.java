package com.sims.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.system.entity.User;

public interface IUserService {
	public void saveOrUpdate(User user, Long id);

	public void delete(User user);

	public void delete(List<Long> ids);

	public Page<User> findAll(Specification<User> spec, Pageable pageable);

	public User findOne(Long id);

	public User findByUserName(String name);

}
