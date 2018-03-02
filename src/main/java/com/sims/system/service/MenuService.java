package com.sims.system.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.common.util.TreeNode;
import com.sims.system.dao.MenuDao;
import com.sims.system.entity.Menu;

@Service
@Transactional
public class MenuService implements IMenuService {

	@Autowired
	private MenuDao meunDao;

	@Override
	public void save(Menu m) {
		// TODO Auto-generated method stub
		meunDao.save(m);
	}

	@Override
	public List<TreeNode> findNodes(Long parentId,Subject subject) {
		// TODO Auto-generated method stub
		//Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		System.out.println(username);
		System.out.println(1111);
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		List<Menu> menuList;
		if (parentId == null) {
			menuList = meunDao.findParentNodes();
		} else {
			menuList = meunDao.findChildNodes(parentId);
		}
		for (Menu m : menuList) {
			TreeNode node = new TreeNode();
			System.out.println(m.getPerm());
			
			if (!subject.isPermitted(m.getPerm())) {//.isPermitted(m.getPerm())
				continue;
			}

			node.setId(m.getId());
			node.setText(m.getMenuName());
			node.setIconCls(m.getIconCls());
			node.setViewType(m.getViewType());
			if (m.getChildNodes() != null) {
				if (m.getChildNodes().size() > 0) {
					node.setLeaf(false);// 设置为父节点
				} else {
					node.setLeaf(true);// 设置为子节点
				}
			}
			nodeList.add(node);
		}
		return nodeList;
	}

}
