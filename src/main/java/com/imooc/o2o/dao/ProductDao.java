package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Product;

public interface ProductDao {
	int insertProduct(Product product);
	List<Product> queryProductList(
			@Param("productCondition") Product productCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	int queryProductCount(@Param("productCondition") Product productCondition);
	
	int updateProduct(Product product);

	int updateProductCategoryToNull(long productCategoryId);

	int deleteProduct(@Param("productId") long productId,
			@Param("shopId") long shopId);
	Product queryProductByProductId(long productId);
}
