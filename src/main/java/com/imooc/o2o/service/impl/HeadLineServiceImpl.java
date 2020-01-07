package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{

	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	
	private static Logger logger=LoggerFactory.getLogger(HeadLineServiceImpl.class);
	@Override
	@Transactional
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException, HeadLineOperationException {
		// TODO Auto-generated method stub
		String key = HLLISTKEY;
		List<HeadLine> headLineList=null;
		ObjectMapper mapper=new ObjectMapper();
		if(headLineCondition!=null&&headLineCondition.getEnableStatus()!=null) {
			key=key+"_"+headLineCondition.getEnableStatus();
		}
		if(!jedisKeys.exists(key)) {
			headLineList=headLineDao.queryHeadLine(headLineCondition);
			String jsonString;
			try {
				jsonString=mapper.writeValueAsString(headLineList);
			}catch (JsonProcessingException e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}else {
			String jsonString=jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				headLineList=mapper.readValue(jsonString, javaType);
				
			}catch(JsonMappingException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}catch (IOException e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		}
		return headLineList;
	}

}
