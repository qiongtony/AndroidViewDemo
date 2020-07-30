package com.example.kotlinleanlib

// interface这样更类似抽象类啊？！
interface Clickable{
    fun click()

    // 带默认实现的方法
    fun println() = println("I'm clickable")
}

interface Focusable{
    fun println() = println("I'm focusable")
}

// 使用：代替extent或implement
class Button : Clickable, Focusable{
    // 使用override代表重写
    override fun click() {
        println("I was clicked")
    }

    // 如果多个接口都实现了同一方法，需要显示实现，可以调用多个接口的默认实现
    override fun println() {
        super<Clickable>.println()
        super<Focusable>.println()
    }
}

// 类或类内的方法默认是final的，需要可重写或继承需加上“open"关键字
open class RichButton  : Clickable{
    fun disable(){

    }

    // 该方法可被重写
    open fun animate(){
        println("RichButton animating...")
    }

    // 重写基类或接口的成员方法默认是open的，如果不想被重写，加上"final"关键字
    override fun click() {
        TODO("Not yet implemented")
    }

    // 可见性修饰符：默认（public）、internal、protected、private
    internal open class TalktiveButton : Focusable{
        private fun yell() = println("Hey!")
        protected fun whisper() = println("Let's talk")
    }

    // 无法实现扩展函数，这里的原因是扩展函数的可见性高于TalktiveButton，所以无法实现
//    fun TalktiveButton.giveSpeech() {
        // 无法访问
//        yell()
        // 无法访问,protected只能在子类访问，而扩展函数是传入接收者实例

//        whisper()
//    }

    class Outer{
        // “inner”：内部类可调用外部类引用，默认与java的静态内部类效果是一样的
        inner class Inner{
            fun getOuterReference() : Outer = this@Outer
        }
    }
}