package com.zhulidov.user_service_astondev.config;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static void run(Class<?> mainComponent ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String packageToscan = mainComponent.getPackage().getName();
        JavaConfig config = new JavaConfig(packageToscan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        Object component = context.getObject(mainComponent);
        if (component instanceof Runnable){
             ((Runnable) component).run();
        } else if( component instanceof AutoCloseable){
            try{
                mainComponent.getMethod("run").invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


}
