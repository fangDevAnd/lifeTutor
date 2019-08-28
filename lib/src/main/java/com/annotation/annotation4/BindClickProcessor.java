package com.annotation.annotation4;

import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


/**
 * 绑定click的操作
 * <p>
 * 1.实现的方案
 * <p>
 * 找到方法   获得annotation的值  ，绑定click事件
 */

@AutoService(Processor.class)
public class BindClickProcessor extends AbstractProcessor {


    private Messager messager;

    private Elements elementUtils;

    private Map<String, ClassCreatorProxy1> proxyMap = new HashMap<>();


    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(BindClick.class.getCanonicalName());
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
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "processing...");

        proxyMap.clear();

        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindClick.class)) {

            ExecutableElement executableElement = (ExecutableElement) element;

            TypeElement classElement = (TypeElement) executableElement.getEnclosingElement();

            String fullClassName = classElement.getQualifiedName().toString();

            ClassCreatorProxy1 proxy1 = proxyMap.get(fullClassName);

            if (proxy1 == null) {
                proxy1 = new ClassCreatorProxy1(elementUtils, classElement);
                proxyMap.put(fullClassName, proxy1);
            }


            BindClick bindClick = executableElement.getAnnotation(BindClick.class);

            int id = bindClick.value();
            proxy1.putElement(id,executableElement);//代理实现了值和变量的对应
        }


        for (String key : proxyMap.keySet()) {
            ClassCreatorProxy1 proxy1 = proxyMap.get(key);

            try {

                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(proxy1.getProxyClassFullName(), proxy1.getTypeElement());

                Writer writer=jfo.openWriter();

                writer.write(proxy1.generateJavaCode());

                writer.flush();
                writer.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE,"process finish ...");

        return true;
    }


}
