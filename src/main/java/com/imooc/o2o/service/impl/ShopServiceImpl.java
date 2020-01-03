package com.imooc.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.FileUtil;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop,ImageHolder imageHolder) {
		// TODO Auto-generated method stub
		if(shop==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		}
		try {
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			shop.setAdvice("审核中");
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(imageHolder.getImage()!=null) {
					try {
						addShopImg(shop,imageHolder);						
					} catch (Exception e) {
						// TODO: handle exception
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					effectedNum=shopDao.updateShop(shop);
					if(effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, ImageHolder imageholder) {
		// TODO Auto-generated method stub
		String dest = FileUtil.getShopImagePath(shop.getShopId());
		String shopImageAddr=ImageUtil.generateThumbnail(imageholder,dest);
		shop.setShopImg(shopImageAddr);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) {
		if(shop==null||shop.getShopId()==null) return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		else {
			try {
			if(imageHolder.getImage()!=null&&imageHolder.getImageName()!=null&&!"".equals(imageHolder.getImageName())) {
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, imageHolder);
			}
			shop.setLastEditTime(new Date());
			int effectNum=shopDao.updateShop(shop);
			if(effectNum<=0)
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			else {
				shop=shopDao.queryByShopId(shop.getShopId());
				ShopExecution se=new ShopExecution(ShopStateEnum.SUCCESS);
				se.setShop(shop);
				return se;
			}}catch (Exception e) {
				throw new ShopOperationException("modify error:"+e.getMessage());
			}
		}
	}

	@Override
	public Shop getByShopId(long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int shopCount=shopDao.queryShopCount(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(shopCount);
			se.setState(ShopStateEnum.SUCCESS.getState());
			se.setStateInfo(ShopStateEnum.SUCCESS.getStateInfo());
		}else {
			se.setState(ShopStateEnum.NULL_SHOP.getState());
			se.setStateInfo(ShopStateEnum.NULL_SHOP.getStateInfo());
		}
		return se;
	}

}
