package com.zhulidov.user_service_astondev.vision;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;
import com.zhulidov.user_service_astondev.config.annotations.Inject;
import com.zhulidov.user_service_astondev.config.annotations.PostConstruct;
import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.ConsoleRenderer;
import com.zhulidov.user_service_astondev.util.HibernateUtil;
import com.zhulidov.user_service_astondev.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AppComponent
public class UserServiceConsoleInterface  {
    private final Logger log = LoggerFactory.getLogger(UserServiceConsoleInterface.class);
    @Inject
    private  UserService userService;
    @Inject
    private  ConsoleRenderer renderer;
    @Inject
    private Mapper mapper;
    @Inject
    private MenuHandler menuHandler;

    private boolean exit = false;

    public UserServiceConsoleInterface(UserService userService, ConsoleRenderer renderer, Mapper mapper, MenuHandler menuHandler) {
        this.userService = userService;
        this.renderer = renderer;
        this.mapper = mapper;
        this.menuHandler = menuHandler;
    }

  

    public UserServiceConsoleInterface() {
    }

    @PostConstruct
    public  void run() {
        while (!exit) {
           Operation operation = menuHandler.showMainMenu();
           handleChoice(operation);

        }
        
        renderer.printMessage("До свидания!");
        renderer.close();
        HibernateUtil.shutdown();
    }

    private void handleChoice(Operation operation){
        if (operation == null){
            log.info("Неверный выбор");
            return;
        }
        switch (operation){
            case CREATE -> createUser();
            case READ_ALL -> showAllUsers();
            case READ_BY_ID -> findUserById();
            case UPDATE -> updateUser();
            case DELETE -> deleteUser();
            case EXIT -> exit = true;
        }
    }


    private void deleteUser() {
        long id = renderer.promptLong("Введите ID пользователя: ");

        UserDto user = userService.getUserById(id);
        if (user != null){
            userService.deleteUser(id);
           log.info("Пользователь успешно удален");
        } else {
            log.info("Пользователя с таким ID: {}  не существует", id);
        }
    }



    private void updateUser() {
        long id = renderer.promptLong("Введите ID пользователя: ");

        UserDto user = userService.getUserById(id);
        User user1 = mapper.toEntity(user);
        choiceNameEmailOrAge(user1);
        userService.updateUser(mapper.toDto(user1));
        log.info("Пользователь обновлен");
    }

    private  void choiceNameEmailOrAge(User user) {

        String choice = renderer.updateChoice();
        switch (choice){
            case "1" -> {
              String name = renderer.promptInput("Введите новый емэйл");
                user.setEmail(name);
            }
            case "2" -> {
                String email = renderer.promptInput("Введите новое Имя");
                user.setName(email);
            }
            case "3" -> {
                int age = renderer.promptInt("Введите новый возраст");
                user.setAge(age);
            }
        }
    }

    private void findUserById() {
        long id = renderer.promptLong("Введите ID пользователя: ");
      UserDto user =   userService.getUserById(id);
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
            userService.saveUser(mapper.toDto(user));
            log.info("Пользователь успешно создан");
        } else {
           log.warn("Не верные данные");
        }
    }


}

























