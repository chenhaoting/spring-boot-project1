package com.miaoshaproject.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements InitializingBean{
	
	private Validator validator;
	
	//校验方法
	public ValidationResult validate(Object bean) {
		final ValidationResult result=new ValidationResult();
		Set<ConstraintViolation<Object>> constraintViolationSet=validator.validate(bean);
		if(constraintViolationSet.size()>0) {
			result.setHasErrors(true);
			constraintViolationSet.forEach(constraintViolation->{
				String errMsg=constraintViolation.getMessage();
				String propertyName=constraintViolation.getPropertyPath().toString();
				result.getErrorMsgMap().put(propertyName, errMsg);
			});
		}
		return result;
	}
	
	public void afterPropertiesSet() throws Exception{
		//将hibernate validator通过工厂初始化使其实例化
		this.validator=Validation.buildDefaultValidatorFactory().getValidator();
	}
}
