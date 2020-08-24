package com.wuweishan.plugindemo

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils;

import java.io.IOException;
import java.util.Set;

/**
 * 在安装时会生成app/build/transforms/wuTransform，这个可以搞成自定义Task吗？类似微信压缩的那个插件
 */
class MyTransform extends Transform {

    public MyTransform() {
    }

    // Task的名称
    @Override
    public String getName() {
        return "wuTransform";
    }

    // 要对哪些类型的结果进行转换（字节码还是资源文件）
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    // 使用范围是什么（整个project，还是别的）
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        // 整个project
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    // 重写了，你就必须自己把编译工作做了，因为系统不知道你要怎么处理编译过程
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        //这里是空实现，说明你要自己去实现这个过程
        super.transform(transformInvocation);
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider

        inputs.each {
            // 各个依赖所编译成的jar文件
            it.jarInputs.each {
                println "file : ${it.file}"
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
                FileUtils.copyFile(it.file, dest)
            }

            it.directoryInputs.each {
                println "directory : ${it.file}"
                // ./app/build/intermediates/transforms/wuTransform/...
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(it.file, dest)
            }
        }

    }
}
