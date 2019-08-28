package com.annotation.annotaion3;

import com.annotation.annotaion2.BindView;
import com.squareup.javapoet.JavaFile;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


/**
 *
 * 本来想通过框架 实现，发现部分程序不能识别
 *
 *
 */
//@AutoService(Processor.class)
public class BindViewAnnotationProcessor extends AbstractProcessor {


    private Messager messager;

    private Elements elementUtils;

    private Map<String, ClassCreatorProxy> proxyMap = new HashMap<>();


    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(BindView.class.getCanonicalName());
        return annotataions;

    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        messager.printMessage(Diagnostic.Kind.NOTE, "processing...");


        proxyMap.clear();

        //得到所有的注解
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {

            VariableElement varElement = (VariableElement) element;//因为代表的是一个字段，所以使用的是VariableElement

            TypeElement classElement = (TypeElement) varElement.getEnclosingElement();


            String fullClassName = classElement.getQualifiedName().toString();

            ClassCreatorProxy proxy = proxyMap.get(fullClassName);

            if (proxy == null) {
                proxy = new ClassCreatorProxy(elementUtils, classElement);//代理的构造实现的是每一个类
                proxyMap.put(fullClassName, proxy);//proxyMap实现的是每一个类的路径对proxy的对应
            }

            BindView bindAnnotation = varElement.getAnnotation(BindView.class);

            int id = bindAnnotation.value();

            proxy.putElement(id,varElement);//代理实现了值和变量的对应

        }



        for(String key:proxyMap.keySet()){

            ClassCreatorProxy proxyInfo=proxyMap.get(key);

            messager.printMessage(Diagnostic.Kind.NOTE,"-->create "+proxyInfo.getProxyClassFullName());


            JavaFile javaFile=JavaFile.builder(proxyInfo.getmPackageName(),proxyInfo.generateJavaCodeByJavaPoet()).build();

            try {
//                javaFile.writeTo(processingEnv.getFiler());

            }catch (Exception e){
                e.printStackTrace();

            }
        }

        messager.printMessage(Diagnostic.Kind.NOTE,"process finish ...");

        return true;
    }


}
