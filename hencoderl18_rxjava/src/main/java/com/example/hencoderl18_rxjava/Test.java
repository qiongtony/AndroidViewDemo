package com.example.hencoderl18_rxjava;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

class Test {
    void single(){
        // 第一步，创建了一个SingleJust对象，构造器传入String
        Single<String> observable = Single.just("1");
        // 第二步，订阅，调用SingleJust.subscribeActual方法，在该方法内:
        //                                                              1.调用onSubscribe(传入的是已丢弃的disposable);
        //                                                              2.调用singleObserver.onSuccess(String)
        // 总结一下流程，Single.just->create SingleJust(...)->SingleJust.subscribeActual->SingleObserver.onSuccess(...)
        observable.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtilsKt.i("onSubscribe");
            }

            @Override
            public void onSuccess(@NonNull String s) {
                LogUtilsKt.i("onSuccess value = " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                String value = e.getMessage();
                if (value == null){
                    value = e.getClass().getSimpleName();
                }
                LogUtilsKt.i("onError e = " + value);
            }
        });
    }

    // 操作符流程
    void map(){
        // 第一步，创建了一个SingleJust<Integer>对象，构造器传入String
        Single<Integer> observable = Single.just(1);
        // 第二步，执行map操作，创建了一个SingleMap<Integer, String>对象，传入SingleJust<Integer>和Function对象
        Single<String> stringSingle = observable.map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return String.valueOf(integer);
            }
        });
        // 第三步，SingleMap<Integer,String>的subscribeActual方法，首先创建了一个MapSingleObserver，然后会调用
        // 1、mapSingleObserver.onSubscribe->singleObserver.onSubscribe，直接给下游了
        // 2、mapSingleObserver.onSuccess->function.apply(...)->singleObserver.onSuccess
        // 总结，执行1->2->3，2操作符的对象将disposable抛给下游，1调用2的onSuccess->转换->给下游3，onSuccess/onError
        stringSingle.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtilsKt.i("onSubscribe");
            }

            @Override
            public void onSuccess(@NonNull String s) {
                LogUtilsKt.i("onSuccess value = " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                String value = e.getMessage();
                if (value == null){
                    value = e.getClass().getSimpleName();
                }
                LogUtilsKt.i("onError e = " + value);
            }
        });
    };

    void internal(){
        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                Log.i("WWS", "accept value = " + aLong);
            }
        });
    }

    void subscribeOn(){
        // 第一步，创建了IoScheduler，传入SingleSubscribeOn构造函数
        // 第二步，执行SingleSubscribeOn.subscribeAcutal-> 1、构造了SubscribeOnObserver对象，存订阅者和上游，它也实现了runnable，
        //                                                2、执行IoScheduler.scheduleDirect->Worker.schedule(...)->Exectuors.submit(Runnable)->(切换到子线程)subscribeOnObserver.onSuccess或onError
        // 总结：将上游和下游封装在Runnable对象中，调用subscribe订阅时，将Runnable放到Executors去执行，执行run方法时切线程，然后执行上游生产，下游消费
        Observable.just(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                    }
                });

        Single.just(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        LogUtilsKt.i("onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        LogUtilsKt.i("onSuccess value = " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtilsKt.i("onError e = " + e.getMessage());
                    }
                });
    }

    public void singleTest(){
        Single.just(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        LogUtilsKt.i("doOnSuccess integer = " + integer + " name = " + Thread.currentThread().getName());
                    }
                }).doFinally(new Action() {
            @Override
            public void run() throws Throwable {
                LogUtilsKt.i("doFinally name = " + Thread.currentThread().getName());
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Throwable {
                LogUtilsKt.i("doAfterTerminate name = " + Thread.currentThread().getName());
            }
        }).subscribe();
    }
}
