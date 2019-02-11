package com.miaoshaproject.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;

@Controller("order")

@RequestMapping("/order")

//解决跨域问题
@CrossOrigin(allowCredentials="true",origins= {"*"})

public class OrderController extends BaseController{
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	//封装下单请求
	@RequestMapping(value="/createorder",method= {RequestMethod.POST},consumes= {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType createOrder(
			@RequestParam(name="itemId")Integer itemId,
			@RequestParam(name="amount") Integer amount,
			@RequestParam(name="promoId",required=false) Integer promoId
			) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Boolean isLogin=(Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
		if(isLogin==null||!isLogin.booleanValue()) {
			throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"未登录");
			
		}
		
		
		//获取用户登录信息
		UserModel userModel=(UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
		
		OrderModel orderModel=orderService.createOrder(userModel.getId(),itemId,promoId, amount);
		
		return CommonReturnType.create(null);
	}
}
