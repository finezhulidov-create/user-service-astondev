package com.zhulidov.user_service_astondev;


import com.zhulidov.user_service_astondev.config.Application;

import com.zhulidov.user_service_astondev.vision.UserServiceConsoleInterface;

import java.lang.reflect.InvocationTargetException;


public class UserServiceAstondevApplication {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Application.run(UserServiceConsoleInterface.class, "com.zhulidov");
    }

}
