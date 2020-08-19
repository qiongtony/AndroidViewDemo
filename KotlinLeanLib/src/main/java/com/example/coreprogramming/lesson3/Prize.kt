package com.example.coreprogramming.lesson3

class Prize(val name : String, val count : Int, val type : Int) {
    // 伴生对象，代替static修饰的类方法和变量
    companion object{
        val TYPE_COMMON = 1
        val TYPE_REDPACK = 2
        val TYPE_COUPON = 3
        fun isRedpack(prize: Prize) : Boolean{
            return prize.type == TYPE_REDPACK
        }

        // 第二个好处，改进工厂方法，其实就是静态的创建方法放在这了嘛
        val defaultCommonPrize = Prize("普通奖品", 10, Prize.TYPE_COMMON)
        fun newRedpackPrize(name : String, count : Int) = Prize(name, count, Prize.TYPE_REDPACK)
        fun newCouponPrize(name: String, count: Int) = Prize(name, count, Prize.TYPE_COUPON)

        fun defaultCommonPrize() = defaultCommonPrize
    }
}