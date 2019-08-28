package com.annotation.annotation;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

class ClassCreator {

    private String bindingClassName;


    private String packageName;

    private TypeElement typeElement;

    private Map<String, VariableElement> variableElementMap = new HashMap<>();


    public ClassCreator(Elements elementUtils, TypeElement typeElement) {

        this.typeElement = typeElement;

        PackageElement packageElement = elementUtils.getPackageOf(typeElement);


        String packageName = packageElement.getQualifiedName().toString();

        String className = typeElement.getSimpleName().toString();

        this.packageName = packageName;

        this.bindingClassName = className + "_BindValue";

    }

    public void putElement(String value, VariableElement element) {
        variableElementMap.put(value, element);
    }

    public String generateJavaCode() {

        StringBuilder builder = new StringBuilder();

        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("public class ").append(bindingClassName);
        builder.append("{\n");

        generareMethods(builder);

        builder.append("\n}\n");

        return builder.toString();

    }

    private void generareMethods(StringBuilder builder) {

        builder.append("public void bind("+typeElement.getQualifiedName()+" host)\n");
        builder.append("{\n");

        for (String value : variableElementMap.keySet()) {

            VariableElement element = variableElementMap.get(value);

            String name = element.getSimpleName().toString();

//            String type = element.asType().toString();

            builder.append("host."+name).append("=\""+value+"\";");
        }
        builder.append("}");
    }



    public String getClassFullName() {
        return packageName+"."+bindingClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }
}
