package com.miaoshaproject.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miaoshaproject.dao.UserDoMapper;
import com.miaoshaproject.dao.UserPasswordDoMapper;
import com.miaoshaproject.dataobject.UserDo;
import com.miaoshaproject.dataobject.UserPasswordDo;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;



@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDoMapper userDoMapper;
	
	@Autowired
	private UserPasswordDoMapper userPasswordDoMapper;
	
	@Autowired
	private ValidatorImpl validator;
	
	
	
	public UserModel getUserById(Integer id) {
		//UserDo不会透露给前端
		UserDo userDo=userDoMapper.selectByPrimaryKey(id);
		if(userDo==null) {
			return null;
		}
		//获取加密密码
		UserPasswordDo userPasswordDo=userPasswordDoMapper.selectByUserId(userDo.getId());
		
		return convertFromDataObject(userDo,userPasswordDo);
	
	} 
	
	private UserModel convertFromDataObject(UserDo userDo,UserPasswordDo userPasswordDo) {
		if(userDo==null) {
			return null;
		}
		UserModel userModel=new UserModel();
		BeanUtils.copyProperties(userDo, userModel);
		
		if(userPasswordDo!=null) {
			userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
		}
		return userModel;
	}

	@Transactional
	public void register(UserModel userModel) throws BusinessException {
		// TODO Auto-generated method stub
		if(userModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		//做验证
		/*if(StringUtils.isEmpty(userModel.getName())
				||userModel.getGender()==null
				||userModel.getAge()==null
				||StringUtils.isEmpty(userModel.getTelphone())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}*/
		
		ValidationResult result=validator.validate(userModel);
		if(result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
		}
		
		
		
		//实现model-》dataobject方法

		UserDo userDo=convertFromModel(userModel);
		try {
			userDoMapper.insertSelective(userDo);
		}catch(DuplicateKeyException ex) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已被注册");
		}
		
		userModel.setId(userDo.getId());
		UserPasswordDo userPasswordDo=convertPasswordFromModel(userModel);
		userPasswordDoMapper.insertSelective(userPasswordDo);
		
		return;
	}
	
	private UserDo convertFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		UserDo userDo=new UserDo();
		BeanUtils.copyProperties(userModel, userDo);
		return userDo;
	}
	
	private UserPasswordDo convertPasswordFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		UserPasswordDo userPasswordDo=new UserPasswordDo();
		userPasswordDo.setEncrptPassword(userModel.getEncrptPassword());
		userPasswordDo.setUserId(userModel.getId());
		return userPasswordDo;
	}

	public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
		// 通过手机获取用户信息
		UserDo userDo=userDoMapper.selectByTelphone(telphone);
		if(userDo==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		UserPasswordDo userPasswordDo=userPasswordDoMapper.selectByUserId(userDo.getId());
		UserModel userModel=convertFromDataObject(userDo,userPasswordDo);
		
		
		
		//比对信息检查是否相符
		if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		return userModel;
	}
}
