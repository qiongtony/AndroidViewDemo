package com.example.coreprogramming.lesson5

// in版的copy，因为支持写，所以放在dest里
fun <T> copyIn(dest : Array<in T>, src : Array<T>){
    if (dest.size < src.size){
        throw IndexOutOfBoundsException()
    }else{
        src.forEachIndexed { index, value ->
            dest[index] = src[index]
        }
    }
}

// out版的copy，支持读，所以放在src里
fun <T> copyOut(dest: Array<T>, src: Array<out T>){
    if (dest.size < src.size){
        throw IndexOutOfBoundsException()
    }else {
        src.forEachIndexed{index, value -> {
            dest[index] = src[index]
        }}
    }
}

class TypeChange {
}