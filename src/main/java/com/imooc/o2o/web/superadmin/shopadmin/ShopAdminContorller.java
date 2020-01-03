package com.imooc.o2o.web.superadmin.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class ShopAdminContorller {
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	@RequestMapping(value = "/productcategorymanagement")
	public String productCategoryManagement() {
		return "shop/productcategorymanagement";
	}
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		return "shop/productedit";
	}
	@RequestMapping(value = "/productmanage")
	public String productManagement() {
		return "shop/productmanage";
	}
}
