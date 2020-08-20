package com.example.coreprogramming.lesson5

fun  main(args : Array<String>){
    val applePlate = FruitPlate<Apple>(Apple(100.0))
    // 简化写法，智能判断
    val applePlateSimple = FruitPlate(Apple(100.0))

    // 不允许，因为Noodles不是Fruit类及子类
//    val noodlesPlate = FruitPlate(Noodles(100.0))

    // 空的水果盘
    val emptyPlate = FruitPlate2(null)

    // 示例：长在地上的水果才可以切
    val watermelon = Watermelon(10.0)
    cut<Watermelon>(watermelon)

    // cut(Apple(2.0)) 报错

    // 使用匿名内部类，获取泛型擦除前的类型信息，为什么呢？类型信息还是会放在对应class的常量池，那匿名类为什么能获取常量池的信息呢？
    val list1 = ArrayList<String>()
    val list2 = object : ArrayList<String>(){}
    // java.util.AbstractList<E>
    println("list1的类型=${list1.javaClass.genericSuperclass}")
    // java.util.ArrayList<java.lang.String> 获取到了泛型的实际类型
    println("使用匿名内部类list2的类型=${list2.javaClass.genericSuperclass}")

    // 结果：java.util.Map<java.lang.String, ? extends java.lang.String>
    println("${object : GenericsToken<Map<String, String>>(){}.type}")

    // 方式2：使用内联函数，打印出来的是class java.lang.Object，好像不太对，原因是什么？
    println("内联函数获取参数类型 ${getType<Map<String, String>>().javaClass.genericSuperclass}")

    // 通过out使得List支持协变，是只读的列表，无法写
}


inline fun <reified T> getType() = T::class.java