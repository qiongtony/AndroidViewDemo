package com.example.coreprogramming.lesson7

fun main(args : Array<String>) {
    // 产生了Area(3.0)对象
    println(Area(1.0) + Area(2.0))

    val list = mutableListOf<Int>(1, 2)
    println("sumIsEven = ${list.sumIsEven}")

    // 编译时是Base，根据多态，运行时是Extended，但调用函数时是静态的，在编译时就确定，所以调用的是Base.fan()方法
    val base : Base = Extended()
    // 调的是foo(Base)方法
    foo(base)
}

// 运算符重载
data class Area (val value : Double)
// 重载"+"运算符，使得Area类可以使用该运算符
operator fun Area.plus(that : Area) : Area{
    return Area(this.value + that.value)
}

// 扩展函数---在类内部声明，只能类内或子类内使用，此时不是static修饰的方法了
// 通过扩展函数，实现列表元素的替换
fun MutableList<Int>.exchange(fromIndex: Int, toIndex : Int){
    val tmp = this[fromIndex]
    this[fromIndex] = this[toIndex]
    this[toIndex] = tmp
}

// 扩展属性，判断和是否为偶数，本质也是static方法，提供了getter/setter而已，所以无法设置默认值
val MutableList<Int>.sumIsEven : Boolean
    get() = this.sum() % 2 == 0

// 扩展属性/方法的一些注意点：在成员方法与扩展方法同时存在时，成员方法>扩展方法，避免
class Son{
    fun foo(){
        println("foo in Class Son")
    }
}

class Parent{
    fun foo(){
        println("foo in Class Parent")
    }

    fun Son.foo2(){
        // 这个是son的foo
        this.foo()
        // 要调用类内的同名方法，需要强制指明@Parent
        this@Parent.foo()
    }
}

open class Base {
    open fun fan(){
        println("I'm Base foo!")
    }
}

class Extended : Base() {
    override fun fan() {
        println("I'm extended foo!")
    }
}

fun foo(base :Base){
    println("foo base")
    base.fan()
}

fun foo(extended: Extended){
    println("foo extended")
    extended.fan()
}

