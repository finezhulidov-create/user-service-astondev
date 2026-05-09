package com.zhulidov.user_service_astondev.config;

import java.lang.reflect.InvocationTargetException;

public interface ObjectConfigurator {

    void configure(Object t, ApplicationContext context) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException;
}
