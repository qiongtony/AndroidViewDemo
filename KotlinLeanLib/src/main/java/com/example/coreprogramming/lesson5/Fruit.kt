package com.example.coreprogramming.lesson5

open class Fruit (val weight :Double)

class Apple(weight: Double) : Fruit(weight)

class Banana(weight: Double) : Fruit(weight)

class Noodles(weight: Double)

// 水果盘只能是Fruit类及子类
class FruitPlate<T : Fruit>(val t : T)

// Fruit类可空
class FruitPlate2<T : Fruit?>(val t : T)

// 通过"where"添加多个约束条件
// 示例：只允许长在地上的水果
interface Ground{}

class Watermelon(weight: Double) : Fruit(weight), Ground

fun <T> cut(t : T) where T : Fruit, T:Ground{
    print("You can cut it")
}