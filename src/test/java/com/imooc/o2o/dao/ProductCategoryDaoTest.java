package com.imooc.o2o.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.ProductCategory;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testBDeleteProductCategory() {
		List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(1L);
		for (ProductCategory productCategory : productCategoryList) {
			int effectNum=productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), productCategory.getShopId());
			System.out.println(effectNum);
			
			
		}
	}
	
	@Test
	
	public void testABatchInsertProductCategory() {
		List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
		ProductCategory p1=new ProductCategory();
		ProductCategory p2=new ProductCategory();
		p1.setCreateTime(new Date());
		p1.setPriority(20);
		p1.setProductCategoryName("月月月月");
		p1.setShopId(9L);
		p2.setCreateTime(new Date());
		p2.setPriority(20);
		p2.setProductCategoryName("日日日日");
		p2.setShopId(9L);
		productCategoryList.add(p1);
		productCategoryList.add(p2);
		int effectNum=productCategoryDao.batchInserctProductCategory(productCategoryList);
		System.out.println(effectNum);
		
	}
	@Test
	public void testCqueryProductCategoryList() {
		long shopId=9L;
		List<ProductCategory> effectNum=productCategoryDao.queryProductCategoryList(shopId);
		System.out.println(effectNum.size());
	}
	
}
