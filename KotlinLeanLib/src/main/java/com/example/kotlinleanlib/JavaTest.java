package com.example.kotlinleanlib;

/**
 * java文件内使用kotlin类
 */
public class JavaTest {
    public void test(){
        Person person = new Person();
        person.setMarried(true);
        System.out.println("person.married = " + person.isMarried());

        // java调用扩展函数，传入的第一个参数的接收者类型
        StringFunctions.lastChar("Java");
    }
}
