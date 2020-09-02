package com.example.annotiondemo.proxy;

import android.util.Log;

public class KoreaOrderService implements OrderService{
    @Override
    public int order() {
        Log.i("WWS", "韩国下单成功：订单号：9999");
        return 9999;
    }
}
