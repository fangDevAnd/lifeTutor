package com.annotation.annotaion3;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ClassCreatorProxy {

    private String mBindingClassName;

    private String mPackageName;

    private TypeElement typeElement;

    private Map<Integer, VariableElement> variableElementMap = new HashMap<>();


    /**
     * @param elementUtils element相关的工具
     * @param classElement 对类数据的相关封装
     */
    public ClassCreatorProxy(Elements elementUtils, TypeElement classElement) {


        typeElement = classElement;

        PackageElement packageElement = elementUtils.getPackageOf(typeElement);

        String packageName = packageElement.getQualifiedName().toString();

        String className = typeElement.getSimpleName().toString();

        mPackageName = packageName;

        mBindingClassName = className + "_ViewBinding";

    }


    public void putElement(int id, VariableElement element) {

        variableElementMap.put(id, element);
    }


    public TypeSpec generateJavaCodeByJavaPoet() {

        TypeSpec bindingClass = TypeSpec.classBuilder(mBindingClassName)
//                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateMethodsByJavaPoet())
                .build();
        return bindingClass;
    }


    private MethodSpec generateMethodsByJavaPoet() {

        ClassName host = ClassName.bestGuess(typeElement.getQualifiedName().toString());
        MethodSpec.Builder methosBuilder = MethodSpec.methodBuilder("bind")
//                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(host, "host");

        for (int id : variableElementMap.keySet()) {

            VariableElement element = variableElementMap.get(id);

            String name = element.getSimpleName().toString();

            String type = element.asType().toString();

            methosBuilder.addCode("host." + name + "=" + "(" + type + ")((android.app.Activity)host).findViewById(" + id + ");\n");
        }

        return methosBuilder.build();
    }


    public String getmPackageName(){
        return mPackageName;
    }



    public String getProxyClassFullName() {
        return mPackageName + "." + mBindingClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }


}
