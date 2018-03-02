package com.sims.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/*
 * 重载FormAuthenticationFilter，使每次验证完权限后都会跳转到首页
 * */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.getAndClearSavedRequest(request);
		WebUtils.redirectToSavedRequest(request, response, "/goindex");
		//issueSuccessRedirect(request, response);
		// we handled the success redirect directly, prevent the chain from continuing:
		return false;
	}
}
