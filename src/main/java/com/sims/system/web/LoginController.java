package com.sims.system.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.SystemLog;
import com.sims.system.entity.Right;
import com.sims.system.entity.Role;
import com.sims.system.entity.User;
import com.sims.system.service.IRightService;
import com.sims.system.service.IUserService;

@Controller
public class LoginController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRightService rightService;

	@RequestMapping(value = "goindex")
	@SystemLog(module = "基础模块", action = "登录系统")
	public String homePage(HttpServletRequest request, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		// 取身份信息
		String username = (String) subject.getPrincipal();
	
		System.out.println(subject.hasRole("root"));
		Role role = userService.findByUserName(username).getRole();
		Set<Right> rights = rightService.findRightByRoleId(role.getId());
		session.setAttribute("userName", username);
		session.setAttribute("roleName", role.getRoleName());
		session.setAttribute("rights", rights);
		return "index";
	}

	@RequestMapping(value = "login")
	public String login(HttpServletRequest request, HttpSession session) throws Exception {
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				session.setAttribute("message", "账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				session.setAttribute("message", "用户名/密码错误");
			} else {
				// 最终在异常处理器生成未知错误
				throw new Exception();
			}
		}
		return "login";
	}

	/*@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		
		session.invalidate();
		System.out.println(11111111);
		
		return "login";
	}*/

	@RequestMapping(value = "refuse")
	public @ResponseBody ExtAjaxResponse refuse() {
		return new ExtAjaxResponse(false, "无权限！");
	}

	/*@RequestMapping(value = "init")
	public @ResponseBody void init(HttpServletRequest request, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		// 取身份信息
		String username = (String) subject.getPrincipal();
		Role role = userService.findByUserName(username).getRole();
		Set<Right> rights = rightService.findRightByRoleId(role.getId());
		session.setAttribute("userName", username);
		session.setAttribute("roleName", role.getRoleName());
		session.setAttribute("rights", rights);
	}*/
	
	@RequestMapping("/changePassword")
	@SystemLog(module = "基础模块", action = "修改密码")
	public @ResponseBody ExtAjaxResponse updatePassword(@RequestParam String userName, @RequestParam String oldpassword, @RequestParam String password1) 
	{	
		try {	
			User currentUser = userService.findByUserName(userName);
			if(!currentUser.getPassword().equals(oldpassword)) {
				return new ExtAjaxResponse(false,"旧密码错误！");
			}
			currentUser.setPassword(password1);
			userService.saveOrUpdate(currentUser,null);
			return new ExtAjaxResponse(true,"操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"操作失败！");
		}
	}

}
