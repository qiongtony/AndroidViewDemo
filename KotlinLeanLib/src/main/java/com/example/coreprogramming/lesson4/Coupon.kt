package com.example.coreprogramming.lesson4

sealed class Coupon() {
    companion object{
        final val CashType = "CASH"
        final val DiscountType = "DISCOUNT"
        final val GiftType = "GIFT"

       final val NotFetched = 1 // 未领取
        final val Feteched = 2 // 已领取但未使用
        final val Used = 3 // 已使用
        final val Expired = 4 // 已过期
        final val UnAvilable = 5 // 已失效
    }
    class CashCoupon(val id: Long, val type : String, val leastCost : Long, val reduceCost : Long) : Coupon()

    class DiscountCoupon(val id: Long, val type :String, val discount : Int) : Coupon()

    class GiftCoupon(val id: Long, val type :String, val gift: String) : Coupon()
}