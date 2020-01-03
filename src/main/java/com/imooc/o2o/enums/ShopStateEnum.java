package com.imooc.o2o.enums;

public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"ShopID为空"),NULL_SHOP(-1003,"Shop为空");
	private int State;
	private String stateInfo;
	private ShopStateEnum(int state,String stateInfo) {
		this.State=state;
		this.stateInfo=stateInfo;
	}
	
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum:values()) {
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
