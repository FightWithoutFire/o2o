package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.dao.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		ShopCategory sc=new ShopCategory();
		sc.setShopCategoryId(3L);
		shopCondition.setShopCategory(sc);
		shopCondition.setOwner(owner);
		ShopExecution se=shopService.getShopList(shopCondition, 0, 2);
		List<Shop> shopList=se.getShopList();
		System.out.println(shopList.size());
		System.out.println(se.getCount());
		System.out.println(se.getState());
		System.out.println(se.getStateInfo());
		
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws FileNotFoundException {
		Shop shop=new Shop();
		shop.setShopId(7L);
		shop.setShopName("中国奶茶");;
		shop.setShopDesc("好奶茶，中国茶");
		File shopImg=new File("D:\\360MoveData\\Users\\xxx\\Desktop\\img\\google.jpg");
		InputStream is=new FileInputStream(shopImg);
		ShopExecution se=shopService.modifyShop(shop,new ImageHolder(shopImg.getName(),is) );
		System.out.println("新图地址："+se.getShop().getShopImg());
		System.out.println(se.getStateInfo());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
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
		File shopImg=new File("D:\\360MoveData\\Users\\xxx\\Desktop\\img\\zzpic14004new.jpg");
		InputStream is=new FileInputStream(shopImg);
		ImageHolder imgHolder = new ImageHolder(shopImg.getName(), is);
		ShopExecution se=shopService.addShop(shop,imgHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
		
	}
}
