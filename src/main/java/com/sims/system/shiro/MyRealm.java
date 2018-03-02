package com.sims.system.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sims.system.entity.Right;
import com.sims.system.entity.Role;
import com.sims.system.entity.User;
import com.sims.system.service.IRightService;
import com.sims.system.service.IUserService;
/*
 * 重载AuthorizingRealm，用户认证以及授权
 * */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRightService rightService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Role role = userService.findByUserName(username).getRole();
		System.out.println(role);
		Set<String> roles = new HashSet<>();
		roles.add(role.getRoleName());
		authorizationInfo.setRoles(roles);
		Set<Right> rights = rightService.findRightByRoleId(role.getId());
		Set<String> right = new HashSet<>();
		for (Right r : rights) {
			System.out.println(r);
			right.add(r.getRightName());
		}
		authorizationInfo.setStringPermissions(right);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = (String) token.getPrincipal();
		User user = userService.findByUserName(username);
		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}
		return new SimpleAuthenticationInfo(user.getUserName(), // 用户名
				user.getPassword(), // 密码
				getName() // realm name
		);
	}

}
