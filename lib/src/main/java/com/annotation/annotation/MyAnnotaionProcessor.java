package com.annotation.annotation;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class MyAnnotaionProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(BindText.class.getCanonicalName());
        return annotataions;

    }


    /**
     * 用于实现对文件的创建
     */
    private Filer filer;

    /**
     * 用于实现对消息的输出
     */
    private Messager messager;


    private Elements elementUtils;


    private Map<String,ClassCreator> creatorMap=new HashMap<>();


    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {

        //返回实现Elements接口的对象，用于操作元素的工具类。
        elementUtils = processingEnv.getElementUtils();

        filer = processingEnv.getFiler();

        messager = processingEnv.getMessager();

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        messager.printMessage(Diagnostic.Kind.NOTE, "processing...");


        creatorMap.clear();

        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(BindText.class);

        for(Element element:set){

            VariableElement varElement = (VariableElement) element;//因为代表的是一个字段，所以使用的是VariableElement

            TypeElement classElement = (TypeElement) varElement.getEnclosingElement();//获得类element


            String fullClassName = classElement.getQualifiedName().toString();//获得类的完整限定名

            ClassCreator creator=creatorMap.get(fullClassName);

            if(creator==null){
                creator=new ClassCreator(elementUtils,classElement);
                creatorMap.put(fullClassName,creator);
            }

            BindText bindTextAnnotation=varElement.getAnnotation(BindText.class);

            String value=bindTextAnnotation.value();

            creator.putElement(value,varElement);
        }


        for(String key:creatorMap.keySet()){

            ClassCreator classCreator=creatorMap.get(key);

            messager.printMessage(Diagnostic.Kind.NOTE,"-->create "+classCreator.getClassFullName());

            try {
                //传递的参数是  类的路径 +类typeElement
                JavaFileObject jfo=filer.createSourceFile(classCreator.getClassFullName(),classCreator.getTypeElement());

                Writer writer=jfo.openWriter();

                writer.write(classCreator.generateJavaCode());

                writer.flush();

                writer.close();


            }catch (IOException e){
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE,"-->create"+classCreator.getClassFullName()+" error!");
            }
        }

        messager.printMessage(Diagnostic.Kind.NOTE,"process finish ...");

        
        return true;
    }





}