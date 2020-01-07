package com.imooc.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.web.superadmin.shopadmin.ProductManagementController;

@Service
public class ProductServiceImpl  implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	private static String SCLISTKEY="productcategorylist";
	private static Logger logger=LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品失败:" + e.toString());
			}
			if (productImgs != null && productImgs.size() > 0) {
				addProductImgs(product, productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
		
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	private void addProductImgs(Product product, List<ImageHolder> productImgs) {
		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> imgAddrList = new ArrayList<ProductImg>();
		if (productImgs != null && productImgs.size() > 0) {
			for (ImageHolder imageHolder : productImgs) {
				String imgAddr=ImageUtil.generateNormalImgs(imageHolder	, dest);
				ProductImg productImg = new ProductImg();
				productImg.setImgAddr(imgAddr);
				productImg.setProductId(product.getProductId());
				productImg.setCreateTime(new Date());
				imgAddrList.add(productImg);
			}
			try {
				int effectedNum = productImgDao.batchInsertProductImg(imgAddrList);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct=productDao.queryProductByProductId(product.getProductId());
				if(tempProduct.getImgAddr()!=null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImageList(product.getProductId());
				addProductImgs(product, productImgs);
			}
			try {
				int effectedNum=productDao.updateProduct(product);
				if(effectedNum<=0) {
					throw new ProductOperationException("更新信息失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("更新信息失败");
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void deleteProductImageList(Long productId) {
		// TODO Auto-generated method stub
		List<ProductImg> productImgList=productImgDao.queryProductImgList(productId);
		for(ProductImg productImg:productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public Product getProductById(long productId) {
		// TODO Auto-generated method stub
		return productDao.queryProductByProductId(productId);
	}

	@Override
	@Transactional
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList=productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count=productDao.queryProductCount(productCondition);
		ProductExecution pe=new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
	
}
