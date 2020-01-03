package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {

	private int state;
	private String stateInfo;
	private int count;
	private ProductCategory productCategory;
	private List<ProductCategory> productCategoryList;

	public ProductCategoryExecution() {

	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, ProductCategory productCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategory = productCategory;
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	
}
