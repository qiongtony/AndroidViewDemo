package com.example.hencoderl28_lib_processor;

import com.example.hencoderl28_lib_annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class MyProcessor extends AbstractProcessor {
    Filer mFiler;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
    }

    /**
     * 生成类代码逻辑
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("annotation processor running");
        // 根元素
        for (Element element : roundEnv.getRootElements()) {
            //element.asType().getKind() = TypeKind.;
            // 往上看，包名
            String packageStr = element.getEnclosingElement().toString();
            String classStr = element.getSimpleName().toString();
            ClassName className = ClassName.get(packageStr, classStr + "_Binding");
            System.out.println("element packageStr = " + packageStr + " classStr = " + classStr + " className = " + className.toString());
            // 生成构造方法描述 XxxBinding(MainActivity activity)
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageStr, classStr), "activity");
            boolean hasBinding = false;

            // 往里，多个，就是内部的成员、方法、内部类
            for (Element enclosedElement : element.getEnclosedElements()) {
                System.out.println("enclosedElement = " + enclosedElement);
                // 看看有没有注解
                BindView bindView = enclosedElement.getAnnotation(BindView.class);
                if (bindView != null) {
                    hasBinding = true;
                    // activity.mTextView = activity.findViewById(R.id.textView);
                    constructorBuilder.addStatement("activity.$N = activity.findViewById($L)",
                            enclosedElement.getSimpleName(), bindView.value());
                }
            }

            TypeSpec builtClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructorBuilder.build())
                    .build();

            if (hasBinding) {
                try {
                    // 创建将类放到新建的类文件内，生成的类文件放到build目录下
                    JavaFile.builder(packageStr, builtClass)
                            .build().writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


//            MethodSpec main = MethodSpec.methodBuilder("main")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .returns(void.class)
//                    .addParameter(String[].class, "args")
//                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
//                    .build();
//            // HelloWorld class
//            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                    .addMethod(main)
//                    .build();
//            try {
//                // build com.example.HelloWorld.java
//                JavaFile javaFile = JavaFile.builder("com.example", helloWorld)
//                        .addFileComment(" This codes are generated automatically. Do not modify!")
//                        .build();
//                // write to file
//                javaFile.writeTo(mFiler);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            // 创建static方法
            MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                    // 添加可见性修饰符
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    // 要添加参数了
                    .addParameter(String[].class, "args")
                    // 方法体内容,$T代表类，会自动引入，$S代表字符串，另外一种引用的方式创建ClassName实例，传入包名及类名
                    .addStatement("$T.out.println($S)", System.class, "Hello Poet!")
                    .build();

            MethodSpec setGreetingMethod = MethodSpec.methodBuilder("setGreeting")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(String.class, "greeting")
                    .addStatement("this.$N = $N", "greeting", "greeting")
                    .build()
                    ;

            // 创建类
            TypeSpec HelloWorldClass = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addField(FieldSpec.builder(String.class, "greeting", Modifier.PRIVATE).build())
                    .addMethod(methodSpec)
                    .addMethod(setGreetingMethod)
                    .build();

            JavaFile javaFile = JavaFile.builder("hencoderl28_lib_annotation", HelloWorldClass)
                    .build();

            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return false;
    }

    /**
     * 支持的类型，看到哪些类型需要执行process
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}