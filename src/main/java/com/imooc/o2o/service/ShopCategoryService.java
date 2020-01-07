package com.imooc.o2o.service;

import java.util.List;

import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryOperationException;

public interface ShopCategoryService {
	public static final String SCLISTKEY="shoptcategorylist";
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) throws ShopCategoryOperationException;
}
