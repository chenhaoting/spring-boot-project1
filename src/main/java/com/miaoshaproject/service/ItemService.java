package com.miaoshaproject.service;

import java.util.List;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;

public interface ItemService {
	//创建商品
	ItemModel createItem(ItemModel itemModel) throws BusinessException;
	//商品列表浏览
	List<ItemModel> listItem();
	//商品详情浏览
	ItemModel getItemById(Integer id);
	
	
	//库存减去
	boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;
		
	//销量加一
	void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}
