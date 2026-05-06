package com.zhulidov.user_service_astondev.config;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static ApplicationContext run(String packageToscan) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        JavaConfig config = new JavaConfig(packageToscan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
