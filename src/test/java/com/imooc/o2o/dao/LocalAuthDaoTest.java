package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
	@Autowired
	private LocalAuthDao  localAuthDao;
	private static final String username="testusername";
	private static final String password="testpassword";
	
	@Test
	@Ignore
	public void testAInsertLocalAuth() {
		LocalAuth localAuth=new LocalAuth();
		PersonInfo personInfo=new PersonInfo();
		personInfo.setUserId(8L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setPassword(password);
		localAuth.setUserName(username);
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		int effectedNum=localAuthDao.insertLocalAuth(localAuth);
		assertEquals(effectedNum, 1);
	}
	
	@Test
	public void testBQueryLocalByUserNameAndPwd(){ 
		LocalAuth localAuth=localAuthDao.queryLocalByUserNameAndPwd(username, password);
		assertEquals("king", localAuth.getPersonInfo().getName());
	
	}
	@Test
	public void testCQueryLocalByUserId() {
		LocalAuth localAuth=localAuthDao.queryLocalByUserId(10L);
		assertEquals("king", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testDUpdateLocalAuth() {
		Date now=new Date();
		int effectedNum=localAuthDao.updateLocalAuth(10L, username, password, "newpassword", now);
		assertEquals(effectedNum, 1);
		LocalAuth localAuth=localAuthDao.queryLocalByUserId(10L);
		System.out.println(localAuth.getPassword());
	}
}
