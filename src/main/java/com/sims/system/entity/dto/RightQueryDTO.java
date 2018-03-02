package com.sims.system.entity.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.sims.system.entity.Right;

public class RightQueryDTO {
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 提供static的工具方法：根据当前 userQueryDTO 对象来组装动态查询条件
	public static Specification<Right> getSpecification(RightQueryDTO rightQueryDTO) {
		Specification<Right> spec = new Specification<Right>() {
			public Predicate toPredicate(Root<Right> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.Predicate查询条件集合
				List<Predicate> list = new ArrayList<Predicate>();

				// 2.根据 QueryDTO数据字段的值进行判断以及条件的组装
				if (null != rightQueryDTO && !StringUtils.isEmpty(rightQueryDTO.getUserName())) {
					Predicate p1 = cb.like(root.get("userName").as(String.class),
							"%" + rightQueryDTO.getUserName() + "%");
					list.add(p1);
				}
				if (null != rightQueryDTO && !StringUtils.isEmpty(rightQueryDTO.getPassword())) {
					Predicate p2 = cb.like(root.get("password").as(String.class),
							"%" + rightQueryDTO.getPassword() + "%");
					list.add(p2);
				}
				// 3.Predicate查询条件集合的 size 创建对应的Predicate查询条件数组
				Predicate[] p = new Predicate[list.size()];
				// 4.CriteriaBuilder的and 函数组装 查询条件数组
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}
}
