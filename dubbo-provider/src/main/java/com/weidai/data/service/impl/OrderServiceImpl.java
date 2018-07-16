package com.weidai.data.service.impl;

import com.weidai.data.api.IOrderService;
import com.weidai.data.dto.OrderParam;
import com.weidai.data.dto.OrderResult;


/**
 * @描述
 * @author nic 
 * @time：2018年7月13日 下午6:19:21
 */
public class OrderServiceImpl  implements IOrderService {

	@Override
	public OrderResult getOrderInfo(OrderParam param) {
		System.out.println("接收到HESSIAN请求：" + param.toString());
		OrderResult result = new OrderResult();
		result.setOrderId(123412313);
		result.setOrderName(param.getUserName() + "HESSIAN购买手机。");
		return result;
	}

}
