package com.shishicai.app.domain;

import java.io.Serializable;

public class Goods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3254041565467228603L;

	private String rec_id;

	private String is_attention;//是否关注  0：否，1：是

	private String goods_id;

	private String goods_name;
	
	private String goods_num;

	private String market_price;	//市场价

	private String shop_price;

	private String promote_price;	//促销价

	private String shop_price_formated; //
	
	private String url;

	private String id;

	private String cate_id;

	private String name;

	private String goods_thumb;	//小图

	private String goods_img;	//带logo大图
	
	private String original_img;	//不带logo原图，比goods_img小
	
	private String goods_sn;	//商品货号
	
	private String size;	//尺码

	private String rank_name;
	
	private String rank_price;
	
	private String img_id;		//pictures
	
	private String img_url;
	
	private String thumb_url;
	
	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}

	public String getIs_attention() {
		return is_attention;
	}

	public void setIs_attention(String is_attention) {
		this.is_attention = is_attention;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public String getShop_price() {
		return shop_price;
	}

	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public String getPromote_price() {
		return promote_price;
	}

	public void setPromote_price(String promote_price) {
		this.promote_price = promote_price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoods_thumb() {
		return goods_thumb;
	}

	public void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public String getOriginal_img() {
		return original_img;
	}

	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public String getRank_price() {
		return rank_price;
	}

	public void setRank_price(String rank_price) {
		this.rank_price = rank_price;
	}

	public String getImg_id() {
		return img_id;
	}

	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getShop_price_formated() {
		return shop_price_formated;
	}

	public void setShop_price_formated(String shop_price_formated) {
		this.shop_price_formated = shop_price_formated;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

}
