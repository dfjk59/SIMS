package com.sims.score.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sims.score.entity.Score;

@Repository
public interface ScoreRepository extends PagingAndSortingRepository<Score,Long>,JpaSpecificationExecutor<Score>
{
	public List<Score> findByCNo(String cNo);
	public List<Score> findByNumber(String number);
}
