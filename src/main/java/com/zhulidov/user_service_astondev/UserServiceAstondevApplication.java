package com.zhulidov.user_service_astondev;


import com.zhulidov.user_service_astondev.config.Application;

import com.zhulidov.user_service_astondev.vision.UserServiceConsoleInterface;




public class UserServiceAstondevApplication {

    public static void main(String[] args)  {
        Application.run(UserServiceConsoleInterface.class);
    }

}
