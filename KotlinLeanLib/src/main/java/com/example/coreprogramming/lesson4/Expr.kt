package com.example.coreprogramming.lesson4

sealed class Expr {
    class Num(value : Int) : Expr()
    class Operate(val opName :String, val left : Expr, val right : Expr) : Expr()

    // 模式匹配
    fun simplifyExpr(expr: Expr) : Expr = when(expr){
        is Expr.Num -> expr
        is Expr.Operate -> when(expr){
            Expr.Operate("+", Expr.Num(0), expr.right) -> expr.right
            Expr.Operate("+", expr.left, Expr.Num(0)) -> expr.left
            else -> expr
        }
    }
}