package com.miaoshaproject.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;


@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private SequenceDOMapper sequenceDOMapper;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderDOMapper orderDOMapper;

	@Autowired
	private UserService userService;
	
	
	
	//保证就算执行失败也要用掉一个sequence，唯一性的策略
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private String generateOrderNo() {
		//订单号16位
		StringBuilder stringBuilder=new StringBuilder();
		//前八位时间信息年月日
		LocalDateTime now=LocalDateTime.now();
		String nowDate=now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
		stringBuilder.append(nowDate);
		//中间6位自增序列
		//获取当前sequence
		int sequence=0;
		SequenceDO sequenceDO=sequenceDOMapper.getSequenceByName("order_info");
		sequence=sequenceDO.getCurrentValue();
		sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
		sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
		String sequenceStr=String.valueOf(sequence);
		for(int i=0;i<6-sequenceStr.length();i++) {
			stringBuilder.append(0);
		}
		stringBuilder.append(sequenceStr);
		
		//后2位分库分表,暂时写成固定的
		
		stringBuilder.append("00");
		return stringBuilder.toString();
	}
	

	private OrderDO convertFromOrderModel(OrderModel orderModel) {
		if(orderModel==null) {
			return null;
		}
		OrderDO orderDO=new OrderDO();
		BeanUtils.copyProperties(orderModel,orderDO);
		
		orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
		
		return orderDO;
	}
	
	
	@Override
	@Transactional
	public OrderModel createOrder(Integer userId, Integer itemId,Integer promoId, Integer amount) throws BusinessException {
		//校验下单状态，下单商品是否存在，用户是否合法等等
		ItemModel itemModel=itemService.getItemById(itemId);
		if(itemModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
			
		}
		UserModel userModel=userService.getUserById(userId);
		if(userModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
		}
		if(amount<=0||amount>99) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不合法的购买数量");
		}
		
		//校验活动信息
		if(promoId!=null) {
			//1  校验活动是否存在
			if(promoId.intValue()!=itemModel.getPromoModel().getId()) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
				
			}else if(itemModel.getPromoModel().getStatus().intValue()!=2){
				//校验是否在活动中
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动没开始");
				
				
			}
		}
		
		
		//两种方案：1落单减库存（√），2支付减库存（不合适此情况舍去）
		boolean result=itemService.decreaseStock(itemId, amount);
		if(!result) {
			throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
			
		}
		//订单入库
		OrderModel orderModel=new OrderModel();
		orderModel.setAmount(amount);
		orderModel.setItemId(itemId);
		orderModel.setItemPrice(itemModel.getPrice());
		orderModel.setUserId(userId);
		if(promoId!=null) {
			orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
		}else {
			orderModel.setItemPrice(itemModel.getPrice());
			
		}
		orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
		orderModel.setPromoId(promoId);
		
		
		//生成交易流水号
		orderModel.setId(generateOrderNo());
		OrderDO orderDO=convertFromOrderModel(orderModel);
		orderDOMapper.insertSelective(orderDO);
		
		
		
		//加上销量
		itemService.increaseSales(itemId, amount);
		
		//返回前端
		
		return orderModel;
	}
	
}
