package com.shishicai.app.domain;

public class Bonus {

	private String bonus_sn;

	private String order_id;

	private String type_name; // 优惠券名

	private String type_money; // 优惠券金额

	private String min_goods_amount; // 最小订单金额

	private String status; // 优惠券状态

	private String use_startdate;

	private String use_enddate; // 截至使用日期

	/**
	 * 使用优惠
	 */
	private String type_id;
	private String bonus_id;
	private String bonus_money_formated;

	public String getBonus_sn() {
		return bonus_sn;
	}

	public void setBonus_sn(String bonus_sn) {
		this.bonus_sn = bonus_sn;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getType_money() {
		return type_money;
	}

	public void setType_money(String type_money) {
		this.type_money = type_money;
	}

	public String getMin_goods_amount() {
		return min_goods_amount;
	}

	public void setMin_goods_amount(String min_goods_amount) {
		this.min_goods_amount = min_goods_amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUse_startdate() {
		return use_startdate;
	}

	public void setUse_startdate(String use_startdate) {
		this.use_startdate = use_startdate;
	}

	public String getUse_enddate() {
		return use_enddate;
	}

	public void setUse_enddate(String use_enddate) {
		this.use_enddate = use_enddate;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getBonus_id() {
		return bonus_id;
	}

	public void setBonus_id(String bonus_id) {
		this.bonus_id = bonus_id;
	}

	public String getBonus_money_formated() {
		return bonus_money_formated;
	}

	public void setBonus_money_formated(String bonus_money_formated) {
		this.bonus_money_formated = bonus_money_formated;
	}

}
