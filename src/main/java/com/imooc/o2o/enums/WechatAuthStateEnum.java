package com.imooc.o2o.enums;

public enum WechatAuthStateEnum {
	SUCCESS(1,"操作成功");
	private int State;
	private String stateInfo;
	private WechatAuthStateEnum(int state,String stateInfo) {
		this.State=state;
		this.stateInfo=stateInfo;
	}
	
	public static WechatAuthStateEnum stateOf(int state) {
		for(WechatAuthStateEnum stateEnum:values()) {
			if(stateEnum.getState()==state)
				return stateEnum;
		}
		return null;
		
	}

	public int getState() {
		return State;
	}


	public String getStateInfo() {
		return stateInfo;
	}
}
