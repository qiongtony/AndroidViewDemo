package com.example.coreprogramming.lesson5

// 学生可为空
data class Seat(val student: Student?)
// 度数可为空
data class Student(val glasses: Glasses?)
data class Glasses(val degreeOfMyopia : Double)

fun getDegree(seat: Seat) :Double{
    return seat.student?.glasses?.degreeOfMyopia?: 0.0
}