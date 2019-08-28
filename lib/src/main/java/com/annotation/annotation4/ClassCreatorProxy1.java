package com.annotation.annotation4;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

class ClassCreatorProxy1 {

    private String bindClassName;

    private String packageName;

    private TypeElement typeElement;

    private Map<Integer, ExecutableElement> executableElementMap = new HashMap<>();


    public ClassCreatorProxy1(Elements elementUtils, TypeElement classElement) {

        this.typeElement = classElement;

        PackageElement packageElement = elementUtils.getPackageOf(typeElement);

        String packageName = packageElement.getQualifiedName().toString();

        String className = typeElement.getSimpleName().toString();

        this.packageName = packageName;

        bindClassName = className + "_ClickBind";

    }


    public void putElement(int id, ExecutableElement element) {

        executableElementMap.put(id, element);
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();

        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("public class ").append(bindClassName).append("{\n");
        generateMetoods(builder);
        builder.append("\n}");
        return builder.toString();
    }

    private void generateMetoods(StringBuilder builder) {

        builder.append("public void bind(final " + typeElement.getQualifiedName() + " host)");

        builder.append("{\n");
        for (int id : executableElementMap.keySet()) {
            ExecutableElement executableElement = executableElementMap.get(id);
            String name = executableElement.getSimpleName().toString();//方法的名称
            String type = executableElement.asType().toString();

            /**
             * 1.host.findViewById(id).setOnC
             *
             *
             *
             */

            builder.append("host.findViewById("+id+")").append(".setOnClickListener(new android.view.View.OnClickListener() {\n");
            builder.append(" @Override\n");
            builder.append("public void onClick(android.view.View v){\n");
            //插入执行代码
            builder.append("host."+name+"(v);");
            builder.append("}\n");
            builder.append("});\n");
        }
        builder.append("}\n");
    }


    public String getProxyClassFullName(){
        return packageName+"."+bindClassName;
    }

    public TypeElement getTypeElement(){
        return typeElement;
    }




}
