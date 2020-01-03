package com.imooc.o2o.service;

import java.io.InputStream;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

public interface ShopService {
	public ShopExecution addShop(Shop shop,ImageHolder imageHolder);
	public ShopExecution modifyShop(Shop shop,ImageHolder imageHolder);
	public Shop getByShopId(long shopId);
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
