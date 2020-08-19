package com.example.coreprogramming.lesson3

import com.example.coreprogramming.lesson2.Basis
import java.util.*

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

    println("可见修饰符：默认、internal、protected和private---------------")
    val car = Classes.BMWCar("A50")
    println(car.getEngine())

    println("委托---------------")
    val flyer = Classes.FlyerDelegate()
    val animal = Classes.AnimalDelegate()
    val b = Classes.BirdDelegate(flyer, animal)
    // 继承的类是A，委托对象是B，C，调用B，C方法时，不需要像组合一样调用A.B.method，而是A.method，其实只是实现了该接口的方法，然后在方法内调具体B或C的方法
    b.fly()
    b.eat()

    // data class的爽
    println("data class---------------")
    val p1 = Person(weight = 1000.00, age = 1, color = "blue")
    val p2 = Person(weight = 1000.00, age = 1, color = "blue")
    // 其实不用判断两次，kotlin中"=="和"equal"是等价的
    println("p1.equal(p2) = ${p1.equals(p2)} p1 == p2 = ${p1 == p2}")
    // data class 的copy是浅拷贝，只拷贝对象的引用，而不是新建一个与对象值相同的对象

    println("component object 伴生对象---------------")
    val prize = Prize("红包", 10, Prize.TYPE_REDPACK)
    println("prize.isRedPack = ${Prize.isRedpack(prize)}")

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

    class Bird4(age : Int){
        val age : Int
        init {
            this.age = age
        }
    }

    // 限制修饰符：open与Java类默认情况一致；默认，与Java带final的类或方法一致，表示不可被继承或重写；abstract与Java的一致
    // 密封类，只能在类内部继承它，不能有实现类，因为它在java中被定义成abstract
    sealed class BirdSealed{
        open fun fly() = "I can fly"
        class Egale : BirdSealed()
    }

    // 可见性修饰符 ：default;internal;private：本Kotlin文件内可见;protected：类及子类可见
    class BMWCar(val name : String){
        private val bMWEngine = Engine("BMW")
        fun getEngine() : String{
            return bMWEngine.engineType()
        }
    }

    private class Engine(val type : String){
        fun engineType() : String{
            return "the engine type is $type"
        }
    }

    // proctected
    class BMWCarProtected(val name: String){
        private val engine = EngineProtected("BMW")
        fun getEngine():String{
            // 访问不了engineType，只有类及子类才能访问
//            return engine.engineType()
            return "can't get"
        }
    }

    private open class EngineProtected(val type : String){
        protected open fun engineType() : String{
            return "the engine type is $type"
        }
    }

    // 子类可以访问engineType方法
    private class BZEngine(type: String) : EngineProtected(type){
        override fun engineType(): String {
            return super.engineType()
        }
    }

    // 通过实现多个接口，模仿多继承效果
    interface FlyerMultiImplements{
        fun fly()
        fun kind() = "flying animals"
    }

    interface Animal{
        val name : String
        fun eat()
        fun kind() = "don't fly animals"
    }

    // 实现接口的方法需要带上override
    class BirdMultiImplements(override val name :String) : FlyerMultiImplements, Animal{
        override fun fly() {
            println("I can fly")
        }

        // 通过super<T>指明，调用的方法
        override fun kind(): String {
          return  super<FlyerMultiImplements>.kind()
        }

        override fun eat() {
            println("I can eat")
        }
    }

    // 通过委托实现多继承
    interface CanFly{
        fun fly()
    }
    interface CanEat{
        fun eat()
    }
    open class FlyerDelegate : CanFly{
        override fun fly() {
            println("I can fly")
        }
    }
    open class AnimalDelegate : CanEat{
        override fun eat() {
            println("I can eat")
        }
    }
    // 加不加后面的委托有什么区别呢
    class BirdDelegate(flyer : FlyerDelegate, animal : AnimalDelegate) : CanFly by flyer, CanEat by animal{}

}