package com.imooc.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	ProductExecution addProduct(Product product,ImageHolder imgholder,
			List<ImageHolder> productImgList)throws ProductOperationException;
	ProductExecution modifyProduct(Product product,ImageHolder imghoder,
			List<ImageHolder> productImgList) throws ProductOperationException;
	Product getProductById(long productId);
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
