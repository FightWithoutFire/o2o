package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {
	private int state;
	private String stateInfo;
	private int count;
	private LocalAuth localAuth;
	private List<LocalAuth> localAuthList;
	
	public LocalAuthExecution() {
	}
	public LocalAuthExecution(LocalAuthStateEnum state) {
		this.stateInfo=state.getStateInfo();
		this.state = state.getState();
	}
	public LocalAuthExecution(LocalAuthStateEnum success, LocalAuth localAuth) {
		// TODO Auto-generated constructor stub
		this.state=success.getState();
		this.stateInfo=success.getStateInfo();
		this.localAuth=localAuth;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public LocalAuth getLocalAuth() {
		return localAuth;
	}
	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}
	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}
	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}
	
}
