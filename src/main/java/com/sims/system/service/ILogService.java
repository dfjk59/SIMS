package com.sims.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.system.entity.Log;

public interface ILogService {
	public void saveOrUpdate(Log log);

	public void delete(Log log);

	public void delete(List<Long> ids);

	public Page<Log> findAll(Specification<Log> spec, Pageable pageable);

	public Log findOne(Long id);
}
