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
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;

import redis.clients.jedis.Jedis;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	

	private static String AREALISTKEY="arealist";
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	@Override
	@Transactional
	public List<Area> getAreaList() throws AreaOperationException {
		// TODO Auto-generated method stub
		String key=AREALISTKEY;
		List<Area> areaList=null;
		ObjectMapper mapper = new ObjectMapper();
		if(!jedisKeys.exists(key)) {
			areaList = areaDao.queryArea();
			String jsonString;
			try {
				jsonString= mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key,jsonString);
			System.out.println("mysql");
		}else {
			System.out.println("jedis");
			String jsonString=jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList=mapper.readValue(jsonString, javaType);
				
			}catch(JsonMappingException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}catch (IOException e) {
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}
	
}
