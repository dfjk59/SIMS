package com.sims.system.service;

import java.util.List;

import org.apache.shiro.subject.Subject;

import com.sims.common.util.TreeNode;
import com.sims.system.entity.Menu;


public interface IMenuService {
	public void save(Menu m);

	public List<TreeNode> findNodes(Long parentId,Subject subject);
}
