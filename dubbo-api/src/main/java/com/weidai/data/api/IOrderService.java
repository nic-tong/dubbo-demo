package com.weidai.data.api;

import com.weidai.data.dto.OrderParam;
import com.weidai.data.dto.OrderResult;


/**
 * @描述
 * @author nic 
 * @time：2018年7月13日 下午6:11:05
 */
public interface IOrderService {

	/**
	 * 根据用户名查询详细订单信息
	 * @param param
	 * @return
	 */
	public OrderResult getOrderInfo(OrderParam param);
}
