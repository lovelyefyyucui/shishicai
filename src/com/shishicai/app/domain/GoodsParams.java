package com.shishicai.app.domain;

import java.io.Serializable;

public class GoodsParams implements Serializable{
	
	private static final long serialVersionUID = 3220118116799032866L;
	/**
	 * 添加购物车传参
	 */
	private int quick;
	private String[] spec;
	private String goods_id;
	private String number;
	private int parent;

	public GoodsParams() {
		super();
	}

	public GoodsParams(int quick, String[] spec, String goods_id, String number,
			int parent) {
		super();
		this.quick = quick;
		this.spec = spec;
		this.goods_id = goods_id;
		this.number = number;
		this.parent = parent;
	}

	public int getQuick() {
		return quick;
	}

	public void setQuick(int quick) {
		this.quick = quick;
	}

	public String[] getSpec() {
		return spec;
	}

	public void setSpec(String[] spec) {
		this.spec = spec;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
}
