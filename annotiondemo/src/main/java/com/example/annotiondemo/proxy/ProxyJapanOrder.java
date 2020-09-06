package com.example.annotiondemo.proxy;

import android.util.Log;

/**
 * 静态代理
 */
public class ProxyJapanOrder implements OrderService{
    private OrderService mOrderService;

    public void setOrderService(OrderService orderService) {
        mOrderService = orderService;
    }

    @Override
    public int order() {
        Log.i("WWS", "通过日本代理下单");
        return mOrderService.order();
    }
}
