package com.example.coreprogramming.lesson6

fun main(args : Array<String>){
     bindData(ContentBean("标题", 10F, "哈哈哈哈这是内容！"))

    map()

    listTest()

    inlineTest()
}

// with、apply内可以直接调用该对象的属性和方法
fun bindData(bean :ContentBean){
    var titleText : String
    var  titleTextSize : Float
    var  contentText : String
    with(bean){
        titleText = this.title // this可以省略
        titleTextSize = textSize
        contentText = content
    }
    println("titleText = $titleText titleTextSize = $titleTextSize contentText = $contentText")

    // apply
    bean.apply {
        titleText = "newTitle${this.title}" // this可以省略
        titleTextSize = textSize
        contentText = "newContent${content}"
    }
    println("titleText = $titleText titleTextSize = $titleTextSize contentText = $contentText")
}

// 通过map函数，使list内的元素都乘以2
fun map(){
    val list = listOf(1, 2, 3, 4, 5)
//    val newList = list.map { it * 2 }

    // 其实用的是高阶函数，等同于
    val newList = list.map { e1 -> e1 * 2 }

    // 也等同于
    val newList2 = list.map { foo(it) }
    println("newList = ${newList.toString()}")
}

fun foo(bar : Int) = bar * 2

// 只读list内的元素也是可以改变的
// 第一种：使用多态的方式，因为MutableList是List的子类，所以通过修改MutableList内的元素可以达到修改只读List的效果
fun listTest(){
    val writeList = mutableListOf(1, 2, 3, 4)
    val readList : List<Int> = writeList
    writeList[0] = 0
    println("readList = ${readList}")

    var readListNew = listOf(1, 2, 3, 4)
    bar(readListNew)


    // sequence的好处，一个不会频繁创建新的List，另外在末端操作执行的时候才会去执行
    var newList = readListNew.asSequence().filter { it > 2}.map { it  * 2 }.toList()
    println("after sequence newList = $newList")
    // sequence包含两类操作：1、中间操作；2、末端操作
    // 中间操作：不会立即执行---惰性求值
    // 所以这里不会打印任何信息
    readListNew.asSequence().filter {
        print("filter value = $it")
        it > 2
    }.map {
        print("map value = $it")
        it * 2 }

    // 末端操作：链式的最后，执行这个才会让整个sequence执行
    // 另外是执行的顺序，序列是遍历的时候逐个执行的，比如有A和B两个中间操作，则执行是1A->1B,2A->2B,而普通的列表处理是求出新的列表，在用新的列表去后面的操作
    // 这带来一个优化，就是把筛选条件放在前面，这样可以减少后面操作的执行次数
    readListNew.asSequence().filter {
        println("filter value = $it")
        it > 2
    }.map {
        println("map value = $it")
        it * 2 }.toList()

    // 通过sequence创建无限序列
    // 构造一个自然数列表
    var naturalNumList = generateSequence(0) { it + 1}
    println("打印前10自然数的列表， = ${naturalNumList.takeWhile {  it <= 9}.toList()}");

}

// 第二种，与Java调用->Java不区分可读和可修改List，List都是可修改的
fun bar(list :List<Int>){
    println(ListTest.foo(list))
}

fun inlineTest(){
    // 第一种方式会创建Function对象
    fooNew{
        println("dive into Kotlin")
    }

    // 第二种方式会使用拷贝的方式，将lambda函数的内容拷到fooInline内，
    fooInline {
        println("dive into Kotlin")
    }

    fooNoInline({
        println("I'm inline")
    }, {
        println("I'm noinline")
    })

    // 这样是会报错的，在lambda里不允许return，这时候有两种解决方案：1、inline；2、加标签
//    fooReturn {
//        return
//    }

    // 非局部返回，这时候和普通的调用执行不一样，是inline会让lambda拷贝过来，导致return放到了内部
    fooReturnInline {
        return
    }

    // 方式2,效果与内联函数一致
    fooReturn {
        return@fooReturn
    }

    // 禁止return语句，"crossinline"，显示指明inline内不能使用return，防止流程的终端
    fooCrossinline {
      //  return // 这里会报错
    }
}

fun fooNew(block : () -> Unit){
    println("before block")
    block()
    println("after block")
}

inline fun fooInline(block : () -> Unit){
    println("before block")
    block()
    println("after block")
}

// 通过在lambda函数前声明"noinline"，表明部分不内联
inline fun fooNoInline(block1: () -> Unit, noinline block2 : () -> Unit){
    block1()
    block2()
}

// inline实现非局部返回
fun fooReturn(returning: () -> Unit){
    println("before block")
    returning()
    println("after block")
}

inline fun fooReturnInline(returning: () -> Unit){
    println("before block")
    returning()
    println("after block")
}

inline fun fooCrossinline(crossinline returning: () -> Unit){
    println("before block")
    returning()
    println("after block")
}
