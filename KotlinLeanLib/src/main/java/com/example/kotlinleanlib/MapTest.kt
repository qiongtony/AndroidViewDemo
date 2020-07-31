package com.example.kotlinleanlib

import java.io.BufferedReader
import java.lang.NumberFormatException
import java.util.*


class MapTest {
    var binaryReps = TreeMap<Char, String>()

    fun execute(){
        generateMap()

        traverseMap()
    }

    /**
     * 保存map，和保存在数组类似
     */
    private fun generateMap() {
        for (c in 'A'..'F') {
            val binary = Integer.toBinaryString(c.toInt())
            binaryReps[c] = binary
        }
    }

    /**
     * 遍历Map
     */
    fun traverseMap(){
        for ((letter, binary) in binaryReps){
            println("$letter = $binary")
        }
    }

    fun forWithIndex(){
        val list = arrayListOf<String>("10", "11", "1010")
        for ((index, element) in list.withIndex()){
            println("$index: $element")
        }
    }

    /**
     * in 代表范围闭区间
     */
    fun recognize(c : Char) = when(c){
        in '0' .. '9' -> "It's a digit"
        in 'a' .. 'z', in 'A' ..'Z' -> "It's a letter!"
        else -> "I don't know..."
    }

    /**
     * 抛出异常
     * 不用显示抛出，只需要在返回值加个“?"即可
     */
    fun readNumber(reader : BufferedReader) : Int ? {
        try {
            val line = reader.readLine()
            return Integer.parseInt(line)
        }catch (e : NumberFormatException){
            return null
        }finally {
            reader.close()
        }
    }

    /**
     * try也是一个表达式，最后一行表达式是返回值
     */
    fun readNumberAndPrint(reader: BufferedReader){
        val number = try {
            Integer.parseInt(reader.readLine())
        }catch (e : NumberFormatException){
            null
        }
        println(number)
    }
}