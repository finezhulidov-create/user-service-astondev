package com.zhulidov.user_service_astondev.vision;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;
import com.zhulidov.user_service_astondev.config.annotations.Inject;
import com.zhulidov.user_service_astondev.config.annotations.PostConstruct;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.ConsoleRenderer;
import com.zhulidov.user_service_astondev.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AppComponent
public class UserServiceConsoleInterface  {
    private final Logger log = LoggerFactory.getLogger(UserServiceConsoleInterface.class);
    @Inject
    private  UserService userService;
    @Inject
    private  ConsoleRenderer renderer;
    private boolean exit = false;

    public UserServiceConsoleInterface(UserService userService, ConsoleRenderer renderer) {
        this.userService = userService;
        this.renderer = renderer;
    }

    public UserServiceConsoleInterface() {
    }

    @PostConstruct
    public  void run() {
        while (!exit) {
           renderer.printMenu();

            int choice = renderer.promptInt("");
            handleChoice(choice);

        }
        
        renderer.printMessage("До свидания!");
        renderer.close();
        HibernateUtil.shutdown();
    }

    private void handleChoice(int choice) {
        
        switch (choice) {
            case 1:
                createUser();
                break;
            case 2:
                showAllUsers();
                break;
            case 3:
                findUserById();
                break;
            case 4:
                updateUser();
                break;
            case 5:
                deleteUser();
                break;
            case 0:
                exit = true;
                log.info("Выход из приложения...");
                break;
            default:
                log.info("Неверный выбор. Попробуйте снова.");
        }
        
    }


    private void deleteUser() {
        long id = renderer.promptLong("Введите ID пользователя: ");

        User user = userService.getUserById(id);
        if (user != null){
            userService.deleteUser(id);
           log.info("Пользователь успешно удален");
        } else {
            log.info("Пользователя с таким ID: {}  не существует", id);
        }
    }



    private void updateUser() {
        long id = renderer.promptLong("Введите ID пользователя: ");

        User user = userService.getUserById(id);

        choiceNameEmailOrAge(user);
        userService.updateUser(user);
        log.info("Пользователь обновлен");
    }

    private  void choiceNameEmailOrAge( User user) {

        String choice = renderer.updateChoice();
        switch (choice){
            case "1" -> {
              String name = renderer.promptInput("Введите новый емэйл");
                user.setName(name);
            }
            case "2" -> {
                String email = renderer.promptInput("Введите новое Имя");
                user.setEmail(email);
            }
            case "3" -> {
                int age = renderer.promptInt("Введите новый возраст");
                user.setAge(age);
            }
        }
    }

    private void findUserById() {
        long id = renderer.promptLong("Введите ID пользователя: ");
      User user =   userService.getUserById(id);
      if (user != null){
          log.info("Найденный пользователь: {}", user);
      } else {
          log.warn("Пользователя с таким ID: {}  не существует", id);
      }
    }

    private void showAllUsers() {
        renderer.printUsersTable(userService.getAllUsers());

    }

    private void createUser() {
        String name = renderer.promptInput("Введите имя: ");
        String email = renderer.promptInput("Введите email: ");
        int age = renderer.promptInt("Введите возраст: ");


        if (name != null && email != null && age != 0){
            User user = new User(name,email,age);
            userService.saveUser(user);
            log.info("Пользователь успешно создан");
        } else {
           log.warn("Не верные данные");
        }
    }


}

























