package com.miaoshaproject.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;

@Controller("/item")
@RequestMapping("/item")

//解决跨域问题
@CrossOrigin(allowCredentials="true",origins= {"*"})
public class ItemController extends BaseController{

	@Autowired
	private ItemService itemService;
	
	
	//商品列表
	@RequestMapping(value="/list",method= {RequestMethod.GET})
	@ResponseBody
	public CommonReturnType listItem(){
		List<ItemModel> itemModelList=itemService.listItem();
		
		//使用stream api将list中的itemModel变成ItemVO
		List<ItemVO> itemVOList=itemModelList.stream().map(itemModel->{
			ItemVO itemVO=this.convertVOFromModel(itemModel);
			return itemVO;
		}).collect(Collectors.toList());
		return CommonReturnType.create(itemVOList);

			
	}
		
		
		
	//创建商品控制器
	@RequestMapping(value="/create",method= {RequestMethod.POST},consumes= {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType createItem(@RequestParam(name="title")String title,
	@RequestParam(name="description")String description,
	@RequestParam(name="price")BigDecimal price,
	@RequestParam(name="stock")Integer stock,
	@RequestParam(name="imgUrl")String imgUrl) throws BusinessException {
		//封装service用来创建商品
		ItemModel itemModel=new ItemModel();
		itemModel.setTitle(title);
		itemModel.setDescription(description);
		itemModel.setImgUrl(imgUrl);
		itemModel.setStock(stock);
		itemModel.setPrice(price);
		
		ItemModel itemModelForReturn=itemService.createItem(itemModel);
		ItemVO itemVO=convertVOFromModel(itemModelForReturn);
		
		
		return CommonReturnType.create(itemVO);
		
	}
	
	//获取商品
	@RequestMapping(value="/get",method= {RequestMethod.GET})
	@ResponseBody
	public CommonReturnType getItem(@RequestParam(name="id")Integer id) throws BusinessException {
		ItemModel itemModel=itemService.getItemById(id);
		ItemVO itemVO=convertVOFromModel(itemModel);
		
		return CommonReturnType.create(itemVO);
		
		
		
	}
	
	private ItemVO convertVOFromModel(ItemModel itemModel) {
		
		if(itemModel==null) {
			return null;
		}
		
		ItemVO itemVO=new ItemVO();
		BeanUtils.copyProperties(itemModel, itemVO);
		if(itemModel.getPromoModel()!=null) {
			//有秒杀
			itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
			itemVO.setPromoId(itemModel.getPromoModel().getId());
			itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
			itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
			
		}else {
			itemVO.setPromoStatus(0);
		}
		
		
		return itemVO;
	}
}
