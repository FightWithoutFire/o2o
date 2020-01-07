package com.imooc.o2o.service;

import java.io.IOException;
import java.util.List;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;

public interface HeadLineService {
	public static final String HLLISTKEY="headlinelists";
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException, HeadLineOperationException;
}
