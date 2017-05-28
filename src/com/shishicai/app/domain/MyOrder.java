package com.shishicai.app.domain;

public class MyOrder {

	/**
	 * 我的订单
	 */
	private String order_id;
	private String log_id;
	private String order_sn;
	private String order_time;
	private String order_status,order_status_txt; // 状态信息
	private String total_fee;
	private String handler; // 已确认or已取消

	/**
	 * 订单详情
	 */
	private String user_id;
	private String shipping_status;
	private String pay_status;
	private String postscript;
	private String best_time;
	private String sign_building;
	private String shipping_id;
	private String shipping_name;
	private String pay_id;
	private String pay_name;
	private String how_oos;
	private String goods_amount;
	private String shipping_fee;
	private String order_amount;
	private String referer;
	private String add_time;
	private String formated_bonus;
	private String formated_total_fee;
	private String formated_shipping_fee;
	private String formated_goods_amount;
	private String formated_add_time;
	private String formated_order_amount;
	private String user_name;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getShipping_status() {
		return shipping_status;
	}

	public void setShipping_status(String shipping_status) {
		this.shipping_status = shipping_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getBest_time() {
		return best_time;
	}

	public void setBest_time(String best_time) {
		this.best_time = best_time;
	}

	public String getSign_building() {
		return sign_building;
	}

	public void setSign_building(String sign_building) {
		this.sign_building = sign_building;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getShipping_name() {
		return shipping_name;
	}

	public void setShipping_name(String shipping_name) {
		this.shipping_name = shipping_name;
	}

	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public String getHow_oos() {
		return how_oos;
	}

	public void setHow_oos(String how_oos) {
		this.how_oos = how_oos;
	}

	public String getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(String goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(String shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getFormated_shipping_fee() {
		return formated_shipping_fee;
	}

	public void setFormated_shipping_fee(String formated_shipping_fee) {
		this.formated_shipping_fee = formated_shipping_fee;
	}

	public String getFormated_goods_amount() {
		return formated_goods_amount;
	}

	public void setFormated_goods_amount(String formated_goods_amount) {
		this.formated_goods_amount = formated_goods_amount;
	}

	public String getFormated_add_time() {
		return formated_add_time;
	}

	public void setFormated_add_time(String formated_add_time) {
		this.formated_add_time = formated_add_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFormated_order_amount() {
		return formated_order_amount;
	}

	public void setFormated_order_amount(String formated_order_amount) {
		this.formated_order_amount = formated_order_amount;
	}

	public String getFormated_total_fee() {
		return formated_total_fee;
	}

	public void setFormated_total_fee(String formated_total_fee) {
		this.formated_total_fee = formated_total_fee;
	}

	public String getFormated_bonus() {
		return formated_bonus;
	}

	public void setFormated_bonus(String formated_bonus) {
		this.formated_bonus = formated_bonus;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getOrder_status_txt() {
		return order_status_txt;
	}

	public void setOrder_status_txt(String order_status_txt) {
		this.order_status_txt = order_status_txt;
	}

}
