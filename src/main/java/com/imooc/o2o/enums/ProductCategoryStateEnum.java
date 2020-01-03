package com.imooc.o2o.enums;

public enum ProductCategoryStateEnum {
	SUCCESS(1,"创建成功"),EMPTY_LIST(-1002,"添加数少于1"),INNER_ERROR(-1001,"操作失败"),;
	private int State;
	private String stateInfo;
	private ProductCategoryStateEnum(int state,String stateInfo) {
		this.State=state;
		this.stateInfo=stateInfo;
	}
	
	public static ProductCategoryStateEnum stateOf(int state) {
		for(ProductCategoryStateEnum stateEnum:values()) {
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
