package com.example.annotiondemo.proxy;

import android.util.Log;

public class DomesticOrderService implements OrderService{
    @Override
    public int order() {
        Log.i("WWS", "国内下单成功：订单号：5555");
        return 5555;
    }
}
