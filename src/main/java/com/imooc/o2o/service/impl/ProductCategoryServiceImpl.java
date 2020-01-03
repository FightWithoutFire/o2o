package com.imooc.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl  implements ProductCategoryService{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;
	@Override
	@Transactional
	public List<ProductCategory> getProductCategoryList(long shopId) {
		// TODO Auto-generated method stub
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
		// TODO Auto-generated method stub
		if(productCategoryList!=null&productCategoryList.size()>0) {
			try {
				int effectNum=productCategoryDao.batchInserctProductCategory(productCategoryList);
				if(effectNum<=0) {
					throw new ProductCategoryOperationException("创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				// TODO: handle exception
				throw new ProductCategoryOperationException("batchAddProductCategory error:"+e.getMessage());
			}
		}else{
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
		try {
			int effectNum=productDao.updateProductCategoryToNull(productCategoryId);
			if(effectNum<0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ProductCategoryOperationException("deleteProductCategory error:"+e.getMessage());
		}
		try {
			int effectNum=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectNum<=0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ProductCategoryOperationException("deleteProductCategory error:"+e.getMessage());
		}
	}
}
