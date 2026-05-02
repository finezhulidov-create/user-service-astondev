package com.zhulidov.user_service_astondev.vision;

import com.zhulidov.user_service_astondev.dao.UserDAO;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.HibernateUtil;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class UserServiceConsoleInterface implements Runnable {
    @Override
    public  void run() {
        UserDAO userDAO = new UserDAO();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Консольное приложение с Hibernate ===");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Показать всех пользователей");
            System.out.println("3. Найти пользователя по ID");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    createUser(userDAO, scanner);
                    break;
                case 2:
                    showAllUsers(userDAO);
                    break;
                case 3:
                    findUserById(userDAO, scanner);
                    break;
                case 4:
                    updateUser(userDAO, scanner);
                    break;
                case 5:
                    deleteUser(userDAO, scanner);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Выход из приложения...");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        HibernateUtil.shutdown(); // Закрытие SessionFactory
        scanner.close();
    }

    private void deleteUser(UserDAO userDAO, Scanner scanner) {

    }

    private void updateUser(UserDAO userDAO, Scanner scanner) {
        
    }

    private void findUserById(UserDAO userDAO, Scanner scanner) {
        System.out.println("Введите ID пользователя: ");
        long id = scanner.nextLong();
      User user =   userDAO.getUserById(id);
      if (user != null){
          System.out.println("Найденный пользователь: " + user);
      } else {
          System.out.println("Пользователя с таким ID: "+ id +" не существует");
      }
    }

    private void showAllUsers(UserDAO userDAO) {

         userDAO.getAllUsers()
                .forEach(u-> System.out.print(u.getId()+" "
                        + u.getName() + " "+ u.getEmail()+ " " + u.getAge()));

    }

    private void createUser(UserDAO userDAO, Scanner scanner) {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Введите емэйл: ");
        String email = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (name != null && email != null && age != 0){
            User user = new User(name,email,age);
            userDAO.saveUser(user);
            System.out.println("Пользователь успешно создан");
        } else {
            System.out.println("Не верные данные");
        }
    }
}

























