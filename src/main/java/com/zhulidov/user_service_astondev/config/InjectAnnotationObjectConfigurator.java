package com.zhulidov.user_service_astondev.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, ApplicationContext context) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        for (Field field : t.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Inject.class)){
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }

    }
}
