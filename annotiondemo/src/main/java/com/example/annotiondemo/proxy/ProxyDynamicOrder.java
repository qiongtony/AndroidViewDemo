package com.example.annotiondemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDynamicOrder implements InvocationHandler {
    // 实际执行的角色
    private Object orderService;


    public Object getOrderService() {
        return orderService;
    }

    public void setOrderService(Object orderService) {
        this.orderService = orderService;
    }

    // 通过Proxy动态创建真实角色
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(orderService.getClass().getClassLoader(), orderService.getClass().getInterfaces(), this);

    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return method.invoke(orderService, objects);
    }
}
