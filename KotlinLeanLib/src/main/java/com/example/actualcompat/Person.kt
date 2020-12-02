package com.example.actualcompat

data class Person(val userName : String, private var age : Int, private var address : String) {

    fun incrementAge(){
        age++
    }

    fun moveTo(newAddress : String){
        address = newAddress
    }
}