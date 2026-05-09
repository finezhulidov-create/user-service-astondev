package com.zhulidov.user_service_astondev.config;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig {

    private Reflections scanner;

    public JavaConfig(String packageToscan) {
        this.scanner = new Reflections(packageToscan);
    }

    public <T> Class<? extends T> getImplClass(Class<T> ifc){
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
        return classes.iterator().next();
    }

    public Reflections getScanner() {
        return scanner;
    }
}
