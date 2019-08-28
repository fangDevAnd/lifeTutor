package com.annotation.annotation1;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class MyAnnationProcessor extends AbstractProcessor {


    int round;


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        round++;

        Iterator<? extends TypeElement> iterator = annotations.iterator();

        while (iterator.hasNext()) {
//            messager.printMessage(Diagnostic.Kind.NOTE, "name is " + iterator.next().getSimpleName().toString());
        }





        return false;


    }
}
