package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.dao.BaseTest;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOperationException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest {
	@Autowired
	private LocalAuthService localAuthService;
	@Test
	@Ignore
	public void testABindLocalAuth() throws LocalAuthOperationException {
		LocalAuth localAuth=new LocalAuth();
		PersonInfo personInfo=new PersonInfo();
		String username="testusername1";
		String password="testpassword1";
		personInfo.setUserId(11L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setUserName(username);
		localAuth.setPassword(password);
		LocalAuthExecution lae=localAuthService.bindLocalAuth(localAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),lae.getState());
		localAuth=localAuthService.getLocalAuthByUserId(personInfo.getUserId());
		System.out.println("用户昵称:"+localAuth.getPersonInfo().getName());
		System.out.println("用户密码:"+localAuth.getPassword());
	}
	
	@Test
	public void testBModifyLocalAuth() throws LocalAuthOperationException {
		String username="testusername1";
		String password="testpassword1";
		String newPassword="testnewpassword1";
		LocalAuthExecution lae=localAuthService.modifyLocalAuth(11L, username, password, newPassword);
		assertEquals(lae.getState(), WechatAuthStateEnum.SUCCESS.getState());
		LocalAuth localAuth=localAuthService.getLocalAuthByUsernameAndPwd(username, newPassword);
		System.out.println(localAuth.getPersonInfo().getName());
		
	}
	
}
