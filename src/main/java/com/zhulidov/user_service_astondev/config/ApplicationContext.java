package com.zhulidov.user_service_astondev.config;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private  Map<Class, Object> cache = new HashMap<>();
    private Set<Class<?>> components = new HashSet<>();
    private JavaConfig config;
    private ObjectFactory factory;

    public ApplicationContext(JavaConfig config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<? extends T> implClass = type;
        if (type.isInterface()){
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(AppComponent.class)){
            cache.put(type, t);
        }
        return t;
    }




    public JavaConfig getConfig() {
        return config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }
}
