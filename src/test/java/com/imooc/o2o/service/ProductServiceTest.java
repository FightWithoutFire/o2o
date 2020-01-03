package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.dao.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;
	
	@Test
	public void testProductList() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(8L);
		ProductCategory pc= new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(8L);
		product.setShop(shop);
		product.setProductName("测试商品一");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		File thumbnailFile1=new File("D:\\projectdev\\image\\upload\\images\\item\\shop\\8\\2019101720205824883.jpg");
		InputStream is1=new FileInputStream(thumbnailFile1);
		ImageHolder imgholder=new ImageHolder(thumbnailFile1.getName(),is1);
		File thumbnailFile2=new File("D:\\projectdev\\image\\upload\\images\\item\\shop\\8\\2019101720205824883.jpg");
		InputStream is2=new FileInputStream(thumbnailFile2);
		List<ImageHolder> productList=new ArrayList<ImageHolder>();
		productList.add(new ImageHolder(thumbnailFile1.getName(),is1));
		productList.add(new ImageHolder(thumbnailFile2.getName(),is2));
		ProductExecution pe=productService.modifyProduct(product, imgholder, productList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
		
	}
	
	
}
