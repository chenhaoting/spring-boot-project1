package com.miaoshaproject.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;

import sun.misc.BASE64Encoder;
//名为user的控制器
@Controller("user")

@RequestMapping("/user")

//解决跨域问题
@CrossOrigin(allowCredentials="true",origins= {"*"})

public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	//用户登录
	@RequestMapping(value="/login",method= {RequestMethod.POST},consumes= {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType login(
			@RequestParam(name="telphone")String telphone,
			@RequestParam(name="password") String password
			) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		

		
		//入参校验
		if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||StringUtils.isEmpty(password)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		
		//检查登录是否合法
		UserModel userModel=userService.validateLogin(telphone, this.EncodeByMd5(password));
		
		//将登陆凭证加入session内
		this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
		this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
		
		
		return CommonReturnType.create(null);
	}
	
	//用户注册接口
	
	@RequestMapping(value="/register",method= {RequestMethod.POST},consumes= {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType register(@RequestParam(name="telphone")String telphone,
			@RequestParam(name="otpCode") String otpCode,
			@RequestParam(name="name") String name,
			@RequestParam(name="gender") Integer gender,
			@RequestParam(name="age") Integer age,
			@RequestParam(name="password") String password
			) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		//验证手机号和otpcode是否符合
		String inSessionOtpCode=(String)this.httpServletRequest.getSession().getAttribute(telphone);
		if(!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
		}
		
		//注册流程
		UserModel userModel=new UserModel();
		userModel.setName(name);
		userModel.setGender(new Byte(String.valueOf(gender.intValue())));
		userModel.setAge(age);
		userModel.setEncrptPassword(this.EncodeByMd5(password));
		userModel.setRegisterMode("byphone");
		userModel.setTelphone(telphone);
		userService.register(userModel);
		
		return CommonReturnType.create(null);
		
	}
	
	
	public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//确定计算方法
		MessageDigest md5=MessageDigest.getInstance("MD5");
		BASE64Encoder base64en=new BASE64Encoder();
		
		//加密字符串
		String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}
	
	//用户获取otp短信接口
	@RequestMapping(value="/getotp",method= {RequestMethod.POST},consumes= {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone) {
		//按照一定规则形成验证码
		Random random=new Random();
		int randomInt=random.nextInt(99999);
		randomInt+=10000;
		String otpCode=String.valueOf(randomInt);
		
		//同对应手机号关联,httpsession绑定手机号
		httpServletRequest.getSession().setAttribute(telphone, otpCode);
		
		//发送短信给用户，省略
		System.out.println(telphone+"...."+otpCode);
		return CommonReturnType.create(null);
	}
	
	
	@RequestMapping("/get")
	@ResponseBody
	public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException{
		//调用service服务获取对应id的用户对象，给前端
		UserModel userModel=userService.getUserById(id);
		
		//若用户信息不存在
		if(userModel==null) {
			userModel.setEncrptPassword("111");
			//throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		}
		
		
		//前端可视模型
		UserVO userVO= convertFromModel(userModel);
		
		return CommonReturnType.create(userVO);
	}
	
	//转化核心模型为前端可视模型
	private UserVO convertFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		UserVO userVO=new UserVO();
		BeanUtils.copyProperties(userModel,userVO);
		return userVO;
	}
	
	
}
