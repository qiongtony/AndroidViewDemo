package com.example.annotiondemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annotiondemo.annotation.BindView;
import com.example.annotiondemo.bean.Person;
import com.example.annotiondemo.proxy.DomesticOrderService;
import com.example.annotiondemo.proxy.KoreaOrderService;
import com.example.annotiondemo.proxy.OrderService;
import com.example.annotiondemo.proxy.ProxyDynamicOrder;
import com.example.annotiondemo.proxy.ProxyJapanOrder;
import com.example.annotiondemo.proxy.RealJapanOrderService;
import com.example.annotiondemo.reflect.InjectView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
        mTv.setText("通过注解获取到View！");
        annonationTest();

        reflectTest();

        proxyTest();
    }

    private void annonationTest(){
        WeekDayDemo.setWeekDay(WeekDayDemo.WeekDay.SATURDAY);
        System.out.println("test....");
        Log.i("WWS", WeekDayDemo.getWeekDay().toString());
    }


    /**
     * 步骤：
     * 1、获取Class对象；(获取到class对象类内的基本所有method、constructor、成员属性都可以获取到了，带declare的所有的，不带的是只获取public的）
     * 2、通过构造器创建实例；
     * 3、通过Class对象获取属性，根据类实例与Field对象给私有属性更新值
     */
    private void reflectTest() {
        try {
            Class<?> personClass = Class.forName("com.example.annotiondemo.bean.Person");

            Field field = personClass.getField("name");
            field.setAccessible(true);

            Constructor<?>[] constructors = personClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                Log.i("WWS", "获取全部的constructor对象：" + constructor);
            }
            // 获取特定的constructor对象
            Constructor<Person> constructor = (Constructor<Person>) personClass.getConstructor(String.class, int.class);
            Log.i("WWS", "获取特定的constructor对象：" + constructor);

            // 调用构造器的newInstance（）方法创建对象
            Person person = constructor.newInstance("QiongTony", 25);
            Log.i("WWS", "person.name = " + person.name + " person.age = " + person.getAge());

            field.set(person, "newName");
            Log.i("WWS", "person.name = " + person.name + " person.age = " + person.getAge());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void proxyTest(){
        staticProxyTest();
        dynamicProxy();
    }

    private void staticProxyTest(){
        Log.i("WWS", "静态代理开始----------------");
        RealJapanOrderService japanOrderService = new RealJapanOrderService();
        ProxyJapanOrder proxyJapanOrder = new ProxyJapanOrder();
        proxyJapanOrder.setOrderService(japanOrderService);
        proxyJapanOrder.order();
    }

    private void dynamicProxy(){
        // 传实际对象过去，然后又动态代理获取对象，感觉实现有问题。
        Log.i("WWS", "动态代理开始----------------");
        ProxyDynamicOrder proxyDynamicOrder = new ProxyDynamicOrder();
        // 国内订购
        DomesticOrderService domesticOrderService = new DomesticOrderService();
        proxyDynamicOrder.setOrderService(domesticOrderService);
        OrderService dynamicInstance = (OrderService) proxyDynamicOrder.getProxyInstance();
        dynamicInstance.order();


        // 日本代购
        OrderService orderService = new RealJapanOrderService();
        proxyDynamicOrder.setOrderService(orderService);
        dynamicInstance = (OrderService) proxyDynamicOrder.getProxyInstance();
        dynamicInstance.order();

        // 韩国代购
        OrderService koreaOrderService = new KoreaOrderService();
        proxyDynamicOrder.setOrderService(koreaOrderService);
        dynamicInstance = (OrderService) proxyDynamicOrder.getProxyInstance();
        dynamicInstance.order();
    }
}