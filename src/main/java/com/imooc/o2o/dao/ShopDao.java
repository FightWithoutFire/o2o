package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	Shop queryByShopId(long shopId);

	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int row,
			@Param("pageSize") int pageSize);
	
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
