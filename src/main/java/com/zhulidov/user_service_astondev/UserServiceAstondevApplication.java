package com.zhulidov.user_service_astondev;


import com.zhulidov.user_service_astondev.config.Application;
import com.zhulidov.user_service_astondev.config.ApplicationContext;
import com.zhulidov.user_service_astondev.dao.UserDAO;
import com.zhulidov.user_service_astondev.service.UserServiceImpl;
import com.zhulidov.user_service_astondev.vision.UserServiceConsoleInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class UserServiceAstondevApplication {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Application.run(UserServiceConsoleInterface.class, "com.zhulidov");
    }

}
