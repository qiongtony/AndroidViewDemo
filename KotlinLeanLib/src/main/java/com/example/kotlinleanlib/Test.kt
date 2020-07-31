package com.example.kotlinleanlib

import java.lang.Exception
import java.lang.IllegalArgumentException

class Test {
    /**
     * 函数用“fun”声明，变量名放在前，类型在后
     * 对java的一些API进行了精简
     */
    fun test(value : Int ) {
        println("Hello, kotlin max = " + max(1, 2))

        val person = Person()
        println("kotlin'person married = ${person.isMarried}")
    }

    /**
     * 函数：
     * 1、返回值也在形参声明后面
     * if和三元运算符类似，是表达式，有返回值
     * kotlin除了循环，其余控制结构大多是表达式
     */
    fun max(a : Int, b : Int) : Int{
        return if(a > b) a else b
    }

    /**
     * 表达式函数体
     * 去掉花括号和返回值，类似于lamber？
     */
    fun maxNew(a : Int, b : Int) : Int = if (a > b) a else b

    /**
     * 表达式函数体还可以省去返回值类型，由编译器自行推断
     */
    fun maxNew2(a : Int, b : Int)  = if(a > b) a else b

    /** 变量 **********************************************/
    /**
     * 变量声明两种形式：
     * 不显示声明类型：val 变量名 = 值
     * 显示声明类型：val 变量名 : 类型 = 值
     */
    val answer = 42

    val answerInt : Int = 42

    /**
     * 声明变量的关键字：
     * 可变：var---对应Java普通变量，类型不可变！
     * 不可变：val---对应Java带final的变量
     */

    // 枚举和“when”
    enum class Color(var r : Int, val g : Int, val b : Int){
        // 在常量创建的时候指定属性值
        RED(255, 0, 0), ORANGE(255, 165, 0),
        YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
        INDIGO(75, 0, 130), VIOLET(238, 130, 238);// 这里必须使用分号

        fun rgb() = (r * 256 + g) * 256 + b

        /**
         * 使用when表达式实现类似switch-case的效果
         */
        fun getMnemonic(color : Color) =
                when(color){
                    Color.RED -> "Richard"
                    Color.ORANGE -> "Of"
                    Color.YELLOW -> "York"
                    Color.GREEN -> "Gave"
                    Color.BLUE -> "Battle"
                    Color.INDIGO -> "In"
                    Color.VIOLET -> "Vain"
                }

        /**
         * 在when表达式中使用","，实现多个值合并到一个分支
         */
        fun getWarmth(color : Color) = when(color) {
            Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
            Color.GREEN -> "neutral"
            Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
        }

        /**
         * when可以接收任何对象
         */
        fun mix(c1 : Color, c2 : Color) =
                // set不需要顺序，只要两个对象相等即可，但这里没次判断都需要创建一些Set实例，效率上会低一点
                when(setOf<Color>(c1, c2)){
                    setOf(RED, YELLOW) -> ORANGE
                    setOf(YELLOW, BLUE) -> GREEN
                    setOf(BLUE, VIOLET) -> INDIGO
                    else -> throw Exception("Dirty color")
                }

        /**
         * 不带参数的“when”，自己写判断表达式，性能高一些，但写起来比较麻烦
         */
        fun mixOptimized(c1 : Color, c2 : Color) =
                when {
                    (c1 == RED && c2 == YELLOW) ||
                            (c1 == YELLOW && c2 == RED) ->
                        ORANGE

                    (c1 == YELLOW && c2 == BLUE) ||
                            (c1 == BLUE && c2 == YELLOW) ->
                        GREEN

                    (c1 == BLUE && c2 == VIOLET) ||
                            (c1 == VIOLET && c2 == BLUE) -> INDIGO

                    else -> throw Exception("Dirty color")
                }
    }

    // 智能转换
    interface Expr
    // 用“：”代表实现该接口
    class Num(val value: Int) : Expr
    class Sum(val left : Expr, val right : Expr) : Expr

    /**
     * 类型检查"is"关键字，“as"显示类型转换---类似与强转
     * 智能转换：
     * 当判断类型，不用进行强转编译器，就可以当做该类型使用了
     */
    fun eval(e : Expr) : Int{
        if (e is Num){
            return e.value;
        }
        if (e is Sum) {
            return eval(e.right) + eval(e.left)
        }
        throw IllegalAccessException("Unknown expression")
    }

    /**
     * 由于if本身是表达式，所以可以省略return
     */
    fun evalRefactor(e : Expr) :Int =
            if (e is Num){
                e.value
            } else if (e is Sum){
                eval(e.right) + eval(e.left)
            } else {
                throw IllegalArgumentException("Unknown expression")
            }

    /**
     * 使用when表达式重构
     */
    fun evalRefactorByWhen(e : Expr) : Int =
            when(e) {
                is Num ->
                    e.value
                is Sum ->
                    eval(e.right) + eval(e.left)
                else ->
                    throw IllegalArgumentException("Unknown expression")
            }

    /**
     * "when"或"if"表达式内使用代码块，如果有返回值，则代码块的最后的表达式为返回值---这样要多种判断的好像就没法这么用了
     */
    fun evalWithLogging(e : Expr) : Int =
            when(e) {
                is Num -> {
                    println("num : ${e.value}")
                    e.value
                }

                is Sum -> {
                    val left = evalWithLogging(e.left)
                    val right = evalWithLogging(e.right)
                    println("sum: $left + $right")
                    left + right
                }
                else -> {
                    throw IllegalArgumentException("Unknown expression")
                }
            }

    // 迭代："while"循环和"for"循环，"while"循环与Java的完全一样，所以不需要实例，"for"只有一种形式，是类似于Java"for-each"
        fun fizzBuzz(i : Int) = when{
         i % 15 == 0 -> "FizzBuzz "
         i % 3 == 0 -> "Fizz "
         i % 5 == 0 -> "Buzz "
        else -> "$i "
        }

    /**
     * for(变量名 in 开始值..结束值)
     *  ...
     * 闭区间
     */
    fun testFizzBuzz() {
            for (i in 1..100)
                print(fizzBuzz(i))
        }

    // [0,100]步长2
    fun testFizzBuzzDownTo(){
        for (i in 100 downTo 1 step 2){
            print(fizzBuzz(i))
        }
    }

    // [1,10)右边开区间
    fun testForByUtil(){
        for (i in 1 until 10){
            print(fizzBuzz(i))
        }
    }
}