package com.sims.system.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/*
 * 日志字段
 * */
@Entity
@Table(name = "t_log")
public class Log {
	private Long id;
	private String userName;
	private String module;
	private String action;
	private String requestUrl;
	private String target;
	private String method;
	private String ipAddress;
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date opTime;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getModule() {
		return module;
	}

	public String getAction() {
		return action;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public String getTarget() {
		return target;
	}

	public String getMethod() {
		return method;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
	public Date getOpTime() {
		return opTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

}
