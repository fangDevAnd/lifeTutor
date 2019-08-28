package com.annotation.annotaion2;

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
     *
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


    public String generateJavaCode() {

        StringBuilder builder = new StringBuilder();

        builder.append("package ").append(mPackageName).append(";\n\n");

        builder.append("\n");
        builder.append("public class ").append(mBindingClassName);
        builder.append("{\n");

        generateMethods(builder);

        builder.append("\n");
        builder.append("}\n");
        return builder.toString();
    }


    private void generateMethods(StringBuilder builder) {
        builder.append("public void bind(" + typeElement.getQualifiedName() + " host)\n");
        builder.append("{\n");
        for (int id : variableElementMap.keySet()) {

            VariableElement element = variableElementMap.get(id);

            String name = element.getSimpleName().toString();

            String type = element.asType().toString();

            builder.append("host." + name).append("=");
            builder.append("(" + type + ")((android.app.Activity)host).findViewById(" + id + ");\n");
        }

        builder.append("}\n");
    }


    public String getProxyClassFullName(){
        return mPackageName+"."+mBindingClassName;
    }

    public TypeElement getTypeElement(){
        return typeElement;
    }





}
