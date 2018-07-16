package com.weidai.data.dto;

import java.io.Serializable;


/**
 * @描述
 * @author nic 
 * @time：2018年7月13日 下午6:12:02
 */
public class OrderParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7689362391004796568L;
	
	private int userId;
	private String userName;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "OrderParam [userId=" + userId + ", userName=" + userName + "]";
	}
}
