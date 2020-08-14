package com.example.hencoderl19_io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okio.Buffer;
import okio.Okio;
import okio.Source;

public class MyClass {
    private static final String FILE_PATH = "./hencoderL19_IO/text.txt";
    public static void main(String[] args){
//        io1();
//        io2();
//        io3();
        okio1();
    }

    public static void io1(){
        // 写文件
        // try括号里面可以包实现了Closeable的类，自动帮调用close
        try (OutputStream outputStream = new FileOutputStream(FILE_PATH)){
            outputStream.write('a');
            outputStream.write('b');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io2(){
        // 读文件
        try (FileInputStream is = new FileInputStream(FILE_PATH)){
            System.out.print((char)(is.read()));
            System.out.print((char)(is.read()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void io3(){
        // 读整行
        try (
                // 创建输入流
                FileInputStream is = new FileInputStream(FILE_PATH);
                // 1.创建InputStreamReader---可以读字符，这个必须要，因为BufferedReader的构造器要传入Reader=。=
             InputStreamReader isReader = new InputStreamReader(is);
                // 2.创建BufferedReader---带缓冲，可以读整行
             BufferedReader bufferedReader = new BufferedReader(isReader);
        ){
            // 读中文是乱码，因为不是UTF-8？
//            System.out.println((char) isReader.read());
            System.out.print(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void okio1(){
        try (Source source = Okio.source(new File(FILE_PATH))){
            Buffer buffer = new Buffer();
                source.read(buffer, 1024);
                System.out.println(buffer.readUtf8Line());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}