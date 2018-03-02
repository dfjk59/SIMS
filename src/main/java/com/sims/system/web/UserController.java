package com.sims.system.web;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.SystemLog;
import com.sims.system.entity.User;
import com.sims.system.entity.dto.UserQueryDTO;
import com.sims.system.service.IUserService;

@Controller
@Transactional
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping("/findAll")
	@RequiresPermissions("user:find")
	@SystemLog(module = "用户模块", action = "用户查询")
	public @ResponseBody Page<User> findPage(UserQueryDTO userQueryDTO, ExtPageable pageable) {
		Page<User> page = userService.findAll(UserQueryDTO.getSpecification(userQueryDTO), pageable.getPageable());
		return page;
	}

	@RequestMapping(value = "/saveOrUpdate")
	//@RequiresRoles("student")
	@RequiresPermissions("user:add")
	@SystemLog(module = "用户模块", action = "用户更新")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(User user, Long rid) {
		try {
			userService.saveOrUpdate(user, rid);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "delete")
	@RequiresPermissions("user:delete")
	@SystemLog(module = "用户模块", action = "用户删除")
	public @ResponseBody ExtAjaxResponse delete(Long id) {
		try {
			User user = userService.findOne(id);
			if (null != user) {
				user.setRole(null);
				userService.delete(user);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping(value = "deleteUsers")
	@RequiresPermissions("user:delete")
	@SystemLog(module = "用户模块", action = "用户批量删除")
	public @ResponseBody ExtAjaxResponse delete(Long[] ids) {
		try {
			List<Long> list = Arrays.asList(ids);
			userService.delete(list);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}
}
