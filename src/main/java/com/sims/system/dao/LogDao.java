package com.sims.system.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sims.system.entity.Log;

public interface LogDao extends PagingAndSortingRepository<Log, Long>, JpaSpecificationExecutor<Log> {

}
