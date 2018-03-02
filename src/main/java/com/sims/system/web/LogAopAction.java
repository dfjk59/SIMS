package com.sims.system.web;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sims.common.util.SystemLog;
import com.sims.system.entity.Log;
import com.sims.system.service.ILogService;

@Aspect
@Component
public class LogAopAction {
	@Autowired
	private ILogService logService;

	@Pointcut("@annotation(com.sims.common.util.SystemLog)")
	private void controllerAspect() {
	}

	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Log log = new Log();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String userName = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
		log.setUserName(userName);
		log.setRequestUrl(request.getRequestURI());
		log.setOpTime(new Date());
		log.setIpAddress(getIpAddress(request));
		;
		// 拦截的实体类，就是当前正在执行的controller
		Object target = pjp.getTarget();
		log.setTarget(target.toString().split("@")[0]);
		// 拦截的方法名称。当前正在执行的方法
		String methodName = pjp.getSignature().getName();
		log.setMethod(methodName);
		// 拦截的方法参数
		Object[] args = pjp.getArgs();
		// 拦截的放参数类型
		Signature sig = pjp.getSignature();
		System.out.println();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Class[] parameterTypes = msig.getMethod().getParameterTypes();
		Object object = null;
		// 获得被拦截的方法
		Method method = null;
		try {
			method = target.getClass().getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		if (null != method) {
			// 判断是否包含自定义的注解
			if (method.isAnnotationPresent(SystemLog.class)) {
				SystemLog systemLog = method.getAnnotation(SystemLog.class);
				log.setModule(systemLog.module());
				log.setAction(systemLog.action());
				try {
					object = pjp.proceed();
					logService.saveOrUpdate(log);
				} catch (Throwable e) {
					System.out.println(e);
				}
			} else {// 没有包含注解
				object = pjp.proceed();
			}
		} else { // 不需要拦截直接执行
			object = pjp.proceed();
		}

		return object;
	}

	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (ip != null && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
