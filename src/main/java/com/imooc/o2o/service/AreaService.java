package com.imooc.o2o.service;

import java.util.List;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;

public interface AreaService {
	List<Area> getAreaList() throws AreaOperationException;
}
