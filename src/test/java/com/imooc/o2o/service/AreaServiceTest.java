package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.dao.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;

public class AreaServiceTest extends BaseTest {
	@Autowired
	private AreaService areaService;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList=null;
		try {
			areaList = areaService.getAreaList();
		} catch (AreaOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("东苑", areaList.get(0).getAreaName());
	}
	
	
}
