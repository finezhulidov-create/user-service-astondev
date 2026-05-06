package com.zhulidov.user_service_astondev.util;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;
import com.zhulidov.user_service_astondev.model.User;

import java.util.List;
import java.util.Scanner;
@AppComponent
public class ConsoleRenderer {

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleRenderer() {
    }

    public  void printMenu() {
        System.out.println("\n=== Консольное приложение с Hibernate ===");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Показать всех пользователей");
        System.out.println("3. Найти пользователя по ID");
        System.out.println("4. Обновить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    public int promptInt(String s) {
        while (true){
          try {
              System.out.println(s);
              return Integer.parseInt(scanner.nextLine());
          } catch (NumberFormatException e) {
              printMessage("Введите корректное число");
          }
        }
    }

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void close() {
        scanner.close();
    }

    public void printUsersTable(List<User> allUsers) {
        System.out.println();
        System.out.println();
        System.out.printf("%-5s %-15s %-25s %-8s%n", "ID", "Имя", "Email", "Возраст");
        System.out.println("-".repeat(60));
        allUsers.forEach(u -> {
            String name = truncate(u.getName(), 14);
            String email = truncate(u.getEmail(), 24);
            System.out.printf("%-5d %-15s %-25s %-8d%n",
                    u.getId(), name, email, u.getAge());
        });
        System.out.println("-".repeat(60));
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - "..".length()) + "..";
    }


    public long promptLong(String x) {
        while(true){
            try{
                System.out.println(x);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                printMessage("Введите корректное число");
            }
        }
    }

    public String promptInput(String x) {
        System.out.println(x);
        return scanner.nextLine();
    }

    public String updateChoice() {
        printUpdates();
        return scanner.nextLine();
    }

    private  void printUpdates() {
        System.out.println("1. Обновить емэйл");
        System.out.println("2. ОБновить Имя");
        System.out.println("3. Обновить возраст");
    }
}
