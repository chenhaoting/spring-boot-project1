package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

public interface OrderService {
	//1（更优方案）通过前端上传秒杀活动id，下单接口校验id是否对应且活动已经开始
	//2接口内判断是否存在秒杀，若存在则秒杀价格下单
	OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
	
	
}
