package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		ShopCategory childCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(12L);
		childCategory.setParent(parentCategory);
		shopCondition.setShopCategory(childCategory);
		List<Shop> shopList=shopDao.queryShopList(shopCondition, 0, 6);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println(shopList.size());
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId=6L;
		Shop shop=shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println("areaName:"+shop.getArea().getAreaName());

	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop=new Shop();
		PersonInfo owner=new PersonInfo();
		Area area=new Area();
		ShopCategory shopCategory=new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(3);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("test");
		shop.setShopDesc("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectNum=shopDao.insertShop(shop);
		assertEquals(1, effectNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop=new Shop();
		shop.setShopId(3L);
		shop.setShopName("测试");
		shop.setShopDesc("测试");
		shop.setLastEditTime(new Date());
		int effectNum=shopDao.updateShop(shop);
		assertEquals(1, effectNum);
	}
}
