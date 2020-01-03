package com.imooc.o2o.web.superadmin.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private AreaService areaService;

	@ResponseBody
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId<0) {
			Object currentShopObj=request.getSession().getAttribute("currentShop");
			if(currentShopObj==null) {
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop)currentShopObj;
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect",false);
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user=new PersonInfo();
		request.getSession().setAttribute("user", user);
		user=(PersonInfo) request.getSession().getAttribute("user");
		user.setUserId(1L);
		user.setName("TEST");;
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		pageIndex=1;
		pageSize=8;
		try {
			Shop shopCondition=new Shop();
			shopCondition.setOwner(user);
			ShopExecution se=shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user",user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("success", e.getMessage());
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	private Map<String, Object> getShopId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop=new Shop();
		if(shopId>-1) {
			try {
				shop=shopService.getByShopId(shopId);
				List<Area> areaList=areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
				
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());

		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			System.out.println(e.getMessage());
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
		}
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			shop.setAdvice("审核中");
			ShopExecution se;
			try {
				ImageHolder imgHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				se = shopService.addShop(shop,imgHolder);

				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					@SuppressWarnings("unchecked")
					List<Shop> shopList=(List<Shop>)request.getSession().getAttribute("shopList");
					if(shopList==null||shopList.size()==0) {
						shopList=new ArrayList<Shop>();
					}
					shopList.add(shop);
					request.getSession().setAttribute("shopList", shopList);	
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyshop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			System.out.println(e.getMessage());
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} 
		if (shop != null&&shop.getShopId()!=null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			shop.setAdvice("审核中");
			ShopExecution se;
			try {
				if(shopImg==null) {
					se = shopService.modifyShop(shop, null);
				}else {
					se = shopService.modifyShop(shop, new ImageHolder( shopImg.getOriginalFilename(),shopImg.getInputStream()) );
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺id");
		}
		return modelMap;
	}
//	private static void inputStreamToFile(InputStream ins,File file) throws IOException {
//		OutputStream os=null;
//		try {
//			os=new FileOutputStream(file);		
//
//			int bytesRead=0;
//			byte[] buffer=new byte[1024];
//			while((bytesRead=ins.read(buffer))!=-1) {
//				os.write(buffer,0,bytesRead);
//			}
//			}catch (Exception e) {	
//				throw new RuntimeException("调用inputStreamToFile产生异常"+e.toString());
//			}finally {
//				try {
//					if(os!=null)
//						os.close();
//					if(ins!=null)
//						ins.close();
//				} catch (IOException e2) {
//					throw new RuntimeException("inputStreamToFile关闭异常");
//				}
//			}
//		
//	}
}
