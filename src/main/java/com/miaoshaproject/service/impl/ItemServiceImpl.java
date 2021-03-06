package com.miaoshaproject.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.dataobject.ItemDO;
import com.miaoshaproject.dataobject.ItemStockDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.PromoModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ValidatorImpl validator;

	@Autowired
	private ItemDOMapper itemDOMapper;

	@Autowired
	private PromoService promoService;
	
	@Autowired
	private ItemStockDOMapper itemStockDOMapper;
	
	public List<ItemModel> listItem(){
		List<ItemDO> itemDOList=itemDOMapper.listItem();
		List<ItemModel> itemModelList=itemDOList.stream().map(itemDO->{
			ItemStockDO itemStockDO =itemStockDOMapper.selectByItemId(itemDO.getId());
			ItemModel itemModel=this.convertModelFromDataObject(itemDO, itemStockDO);
			return itemModel;
		}).collect(Collectors.toList());
		return itemModelList;
	}
	
	private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
		if(itemModel==null) {
			return null;
		}
		ItemDO itemDO=new ItemDO();
		BeanUtils.copyProperties(itemModel, itemDO);
	
		itemDO.setPrice(itemModel.getPrice().doubleValue());
		return itemDO;
		
	}
	
	private ItemStockDO convertItemStockFromItemModel(ItemModel itemModel) {
		if(itemModel==null) {
			return null;
		}
		ItemStockDO itemStockDO=new ItemStockDO();
		itemStockDO.setItemId(itemModel.getId());
		itemStockDO.setStock(itemModel.getStock());
		return itemStockDO;
	}
	
	@Transactional
	public ItemModel createItem(ItemModel itemModel) throws BusinessException {
		//校验入参
		ValidationResult result=validator.validate(itemModel);
		if(result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
		}
		//转化itemmodel-》dataobject
		ItemDO itemDO=this.convertItemDOFromItemModel(itemModel);
		
		//写入数据库
		itemDOMapper.insertSelective(itemDO);
		itemModel.setId(itemDO.getId());
		ItemStockDO itemStockDO=this.convertItemStockFromItemModel(itemModel);
		
		itemStockDOMapper.insertSelective(itemStockDO);
		
		
		//返回对象
		
		return this.getItemById(itemModel.getId());
	}

	public ItemModel getItemById(Integer id) {
		ItemDO itemDO=itemDOMapper.selectByPrimaryKey(id);
		if(itemDO==null) {
			return null;
		}
		//操作获取库存数量
		ItemStockDO itemStockDO=itemStockDOMapper.selectByItemId(id);
		
		
		//dataobject->model
		ItemModel itemModel =convertModelFromDataObject(itemDO, itemStockDO);
		
		//获取活动秒杀信息
		PromoModel promoModel=promoService.getPromoByItemId(itemModel.getId());
		if(promoModel!=null&&promoModel.getStatus().intValue()!=3) {
			itemModel.setPromoModel(promoModel);
		}
		
		return itemModel;
	}
	
	private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO) {
		ItemModel itemModel =new ItemModel();
		BeanUtils.copyProperties(itemDO, itemModel);
		itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
		itemModel.setStock(itemStockDO.getStock());
		
		return itemModel;
		
	}

	@Override
	@Transactional
	public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
		
		//数据库中收到影响的条数
		int addectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
		
		if(addectedRow>0) {
			//更新成功
			return true;
			
		}else {
			return false;
		}
		
	}

	@Override
	@Transactional
	public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
		itemDOMapper.increaseSales(itemId, amount);
	}


}
