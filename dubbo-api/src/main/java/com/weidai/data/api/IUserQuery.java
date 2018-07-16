package com.weidai.data.api;

import com.weidai.data.dto.OrderResult;


/**
 * @描述
 * @author nic 
 * @time：2018年7月16日 下午2:16:17
 */
public interface IUserQuery {

	public OrderResult getOrderById(Long id);
}
