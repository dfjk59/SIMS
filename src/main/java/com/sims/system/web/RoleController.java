package com.sims.system.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.SystemLog;
import com.sims.system.entity.Right;
import com.sims.system.entity.Role;
import com.sims.system.entity.dto.RoleQueryDTO;
import com.sims.system.service.IRightService;
import com.sims.system.service.IRoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private IRoleService roleService;

	@Autowired
	private IRightService rightService;

	@RequestMapping("/findAll")
	@RequiresPermissions("role:find")
	@SystemLog(module = "角色模块", action = "角色查询")
	public @ResponseBody Page<Role> findPage(RoleQueryDTO roleQueryDTO, ExtPageable pageable) {
		Page<Role> page = roleService.findAll(RoleQueryDTO.getSpecification(roleQueryDTO), pageable.getPageable());
		return page;
	}

	@RequestMapping(value = "/saveOrUpdate")
	@RequiresPermissions("role:add")
	@SystemLog(module = "角色模块", action = "角色创建")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Role role) {
		try {
			roleService.saveOrUpdate(role);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "delete")
	@RequiresPermissions("role:delete")
	@SystemLog(module = "角色模块", action = "角色删除")
	public @ResponseBody ExtAjaxResponse delete(Long id) {
		try {
			Role role = roleService.findOne(id);
			if (null != role) {
				role.setRights(null);
				role.setUsers(null);
				roleService.delete(role);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "deleteUsers")
	@RequiresPermissions("role:delete")
	@SystemLog(module = "角色模块", action = "角色批量删除")
	public @ResponseBody ExtAjaxResponse delete(Long[] ids) {
		try {
			List<Long> list = Arrays.asList(ids);
			roleService.delete(list);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "setRight")
	@SystemLog(module = "角色模块", action = "设置权限")
	public @ResponseBody ExtAjaxResponse setRight(Long id, Long[] ids) {
		try {
			List<Right> rs = new ArrayList<Right>();
			for (Long rid : ids) {
				Right r = rightService.findOne(rid);
				rs.add(r);
			}
			Role role = roleService.findOne(id);
			role.setRights(rs);
			roleService.saveOrUpdate(role);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}
}
