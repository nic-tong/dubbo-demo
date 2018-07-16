package com.weidai.data.dto;

import java.io.Serializable;


/**
 * @描述
 * @author nic 
 * @time：2018年7月13日 下午6:11:41
 */
public class OrderResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5850404462647246260L;

	private long orderId;
	private String orderName;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	@Override
	public String toString() {
		return "OrderResult [orderId=" + orderId + ", orderName=" + orderName
				+ "]";
	}

	
}
