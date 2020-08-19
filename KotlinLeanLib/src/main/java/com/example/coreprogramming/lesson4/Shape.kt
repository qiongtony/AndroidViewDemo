package com.example.coreprogramming.lesson4

sealed class Shape {
    class Circle(val radius : Double) : Shape()
    class Rectangle(val width : Double, val height : Double) : Shape()
    class Triangle(val base :Double, val height : Double) : Shape()

    // 在调用when里如果声明的类型不够，会自动提示，保证了安全性
    fun getArea() : Double = when(this){
        is Shape.Circle -> Math.PI * radius * radius
        is Shape.Rectangle -> width * height
        is Shape.Triangle -> base * height / 2.0
    }
}