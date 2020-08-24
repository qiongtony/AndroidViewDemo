package com.wuweishan.plugindemo

import com.android.build.gradle.BaseExtension
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

class MyPlugin implements Plugin<Project>{
    @Override
    void apply(Project target) {
        // 这里创建的是扩展的名称，类型这样wuweishan {...}
        ExtensionDemo extension = target.extensions.create('wuweishan', ExtensionDemo)
        // 在配置添加完后才去获取extension的信息，因为引入plugin时所有的代码都会执行，而获取值需要等定义后拿
        target.afterEvaluate(new Action<Project>() {
            @Override
            void execute(Project project) {
                println "Hello:${extension.userName}"
            }
        })
        // 在插件里引入自定义的Transform，在打包时会输出
        def transform = new MyTransform()
        def baseExtension = target.extensions.getByType(BaseExtension)
        // 这里相当于是注册task了吧
        baseExtension.registerTransform(transform)
    }
}