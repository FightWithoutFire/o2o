package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductImg;


public interface ProductImgDao {
	List<ProductImg> queryProductImgList(long productId);

	int batchInsertProductImg(List<ProductImg> productImgList);

	int deleteProductImgByProductId(long productId);
}
