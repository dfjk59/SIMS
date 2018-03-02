package com.sims.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.system.dao.LogDao;
import com.sims.system.entity.Log;

@Service
@Transactional
public class logService implements ILogService {
	@Autowired
	private LogDao logDao;

	@Override
	public void saveOrUpdate(Log log) {
		// TODO Auto-generated method stub
		logDao.save(log);
	}

	@Override
	public void delete(Log log) {
		// TODO Auto-generated method stub
		logDao.delete(log);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		Iterable<Log> all = logDao.findAll(ids);
		if (all != null) {
			logDao.delete(all);
		}
	}

	@Override
	public Page<Log> findAll(Specification<Log> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return logDao.findAll(spec, pageable);
	}

	@Override
	public Log findOne(Long id) {
		// TODO Auto-generated method stub
		return logDao.findOne(id);
	}

}
