package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testInsertShop() {
		ShopCategory shopCategory=new ShopCategory();
		List<ShopCategory> effectNum=shopCategoryDao.queryShopCategory(null);
		System.out.println(effectNum.size());
		
	}
	
}
