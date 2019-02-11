package com.miaoshaproject.controller.viewobject;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class ItemVO {
	private Integer id;
	//是否在秒杀,0就没有，1未开始，2进行中
	private Integer promoStatus;
	//秒杀价格
	private BigDecimal promoPrice;
	//秒杀Id
	private Integer promoId;
	//秒杀开始时间
	private String startDate;
	
	
	//商品名称
	private String title;
	//价格
	private BigDecimal price;
	//库存
	private Integer stock;
	//商品描述
	private String description;
	//商品销量
	private Integer sales;
	//商品图片url
	private String imgUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPromoStatus() {
		return promoStatus;
	}
	public void setPromoStatus(Integer promoStatus) {
		this.promoStatus = promoStatus;
	}
	public BigDecimal getPromoPrice() {
		return promoPrice;
	}
	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}
	public Integer getPromoId() {
		return promoId;
	}
	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
}
