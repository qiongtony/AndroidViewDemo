package com.example.kotlinleanlib;

/**
 * java文件内使用kotlin类
 */
public class JavaTest {
    public void test(){
        Person person = new Person();
        person.setMarried(true);
        System.out.println("person.married = " + person.isMarried());
    }
}
