package com.example.coreprogramming.lesson3

import com.example.coreprogramming.lesson2.Basis

fun  main(args : Array<String>){
    val basis = Basis()
    basis.testInfix()

    println("Bird------------------------")
    val bird = Classes.Bird()
    bird.weight
    println("Bird2------------------------")
    val bird2 = Classes.Bird2(10.00, 2, "red")

    println("Bird3------------------------")
    val bird3 = Classes.Bird3(20.00, 3, "green")

    println("延迟初始化：be lazy和lateinit---------------")

}
class Classes {

    // 接口可以1、带默认实现；2、支持抽象属性（如height)，只能通过get方法赋值常量
    interface Flyer{
        // val height = 1000是不对的，应该调用get
        val height : Int
            get() = 1000

        fun kind()

        // 接口带
        fun fly(){
            println("I can fly")
        }
    }

    class Bird(weight : Double = 0.00, age : Int = 0, color : String = "blue"){
        val weight : Double
        val age : Int
        val color :String
        // 构造方法参数可以在init语句块被调用，其他地方调不到！
        init {
            this.weight = weight
            this.age = age
            this.color = color
        }
    }

    // 等同于上面的定义，声明val表示声明了一个同名的属性，weight和color带了val或var就不用声明了
    class Bird2(val weight :Double = 0.00, age : Int = 0, val color : String = "blue"){
        val age : Int
        init {
            this.age = age
        }
    }

    class Bird3(weight : Double = 0.00, age : Int = 0, color : String = "blue"){
        val weight : Double
        val age : Int
        val color :String

        // 存在多个init代码块时，从上到下顺序执行，便于逻辑的分离
        init {
            println("first init")
            this.weight = weight
            this.age = age
            println("weight = ${this.weight}")
            println("age = ${this.age}")
        }

        init {
            println("second init")
            this.color = color
            println("color = ${this.color}")
        }
    }

    // 延迟初始化
    class BirdLazy(val weight: Double, val age: Int, val color: String){
        // 必须用val声明，首次调用才初始化
        val sex : String by lazy {
            if (color == "yellow") "male" else "female"
        }

        // lateinit适用于var变量，不能用于基本数据类型，其他用法和普通var没有分别
        lateinit var secondSex : String
        fun printSex(){
            this.secondSex = if (this.color == "yellow") "female" else "male"
            println("secondSex = $secondSex")
        }
    }
}