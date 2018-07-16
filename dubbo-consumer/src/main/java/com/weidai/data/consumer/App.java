package com.weidai.data.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weidai.data.api.IOrderService;
import com.weidai.data.dto.OrderParam;
import com.weidai.data.dto.OrderResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
    	IOrderService service = (IOrderService) context.getBean("orderService");
    	OrderParam param = new OrderParam();
    	param.setUserId(1);param.setUserName("hhh");
    	OrderResult result = service.getOrderInfo(param);
    	System.out.println(result);
    }
}
