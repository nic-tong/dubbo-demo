package com.weidai.data.service.impl;

import com.weidai.data.api.IOrderService;
import com.weidai.data.dto.OrderParam;
import com.weidai.data.dto.OrderResult;


/**
 * @描述
 * @author nic 
 * @time：2018年7月13日 下午6:19:21
 */
public class OrderServiceImpl3  implements IOrderService {

	@Override
	public OrderResult getOrderInfo(OrderParam param) {
		System.out.println("接收到请求 版本2.0：" + param.toString());
		OrderResult result = new OrderResult();
		result.setOrderId(123412313);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setOrderName(param.getUserName() + "版本2.0购买手机。");
		return result;
	}

}
