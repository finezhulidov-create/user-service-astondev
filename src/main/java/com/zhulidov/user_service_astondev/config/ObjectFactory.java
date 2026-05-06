package com.zhulidov.user_service_astondev.config;

import com.zhulidov.user_service_astondev.config.annotations.PostConstruct;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)){
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    public <T> T createObject(Class<? extends T> implClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        T t = create(implClass);
        cofigure(t);
        invokeInit(implClass, t);
        return t;
    }

    private <T> void invokeInit(Class<? extends T> implClass, T t) throws InvocationTargetException, IllegalAccessException {
        for (Method method : implClass.getMethods()){
            if (method.isAnnotationPresent(PostConstruct.class)){
                method.setAccessible(true);
                method.invoke(t);
            }
        }
    }

    private <T> void cofigure(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T create(Class<? extends T> implClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return implClass.getDeclaredConstructor().newInstance();

    }
}
