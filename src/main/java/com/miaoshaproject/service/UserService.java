package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {
	//获取用户对象的方法
	UserModel getUserById(Integer id);
	UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
	void register(UserModel userModel) throws BusinessException;
}
