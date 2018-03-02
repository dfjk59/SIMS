package com.sims.system.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.TreeNode;
import com.sims.system.service.IMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private IMenuService menuService;

	@RequestMapping("/find")
	public @ResponseBody List<TreeNode> findNodesByParentId(@RequestParam("node") String node) {
		if (node.equals("root")) {
			return menuService.findNodes(null,SecurityUtils.getSubject());
		} else {
			return menuService.findNodes(Long.parseLong(node),SecurityUtils.getSubject());
		}
	}
}
