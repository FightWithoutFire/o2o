package com.imooc.o2o.enums;

public enum LocalAuthStateEnum {
	NULL_AUTH_INFO(-101,"用户不存在"), ONLY_ONE_ACCOUNT(-102,"用户已被绑定"), SUCCESS(1,"成功");
	private int State;
	private String stateInfo;
	private LocalAuthStateEnum(int state,String stateInfo) {
		this.State=state;
		this.stateInfo=stateInfo;
	}
	
	public static LocalAuthStateEnum stateOf(int state) {
		for(LocalAuthStateEnum stateEnum:values()) {
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
