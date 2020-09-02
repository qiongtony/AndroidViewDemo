package com.example.annotiondemo.proxy;

import android.util.Log;

public class RealJapanOrderService implements OrderService{
    @Override
    public int order() {
        Log.i("WWS", "向日本商家下单");
        return 9999;
    }
}
