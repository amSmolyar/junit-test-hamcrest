package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccessCheck {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String login = "";
        String password = "";

        System.out.println("Привет!");

        int cntAttempt = 10;
        while (cntAttempt > 0) {
            cntAttempt--;

            System.out.println("\nВведите логин:");
            login = scan.nextLine();
            System.out.println("\nВведите пароль:");
            password = scan.nextLine();

            try {
                User user = checkByLoginPassword(login, password);
                if (checkByAge(user, 18))
                    System.out.println("Предоставлен доступ пользователю:\n" + user.toString());
            } catch (UserNotFoundException | AccessDeniedException | NullPointerException e) {
                System.out.println(e.getMessage());
                System.out.println("исключение обработано");
            }
        }

        scan.close();
    }

    public static User checkByLoginPassword(String login, String password) throws UserNotFoundException, NullPointerException {
        List<User> allUser = getUserList();
        if (allUser == null || login == null || password == null)
            throw new NullPointerException();

        for (User ii : allUser) {
            if (login.equals(ii.getLogin())) {
                if (password.equals(ii.getPassword())) {
                    return ii;
                } else {
                    throw new UserNotFoundException("Wrong password");
                }
            }
        }
        throw new UserNotFoundException("User with login " + login + " is not registered");
    }

    public static boolean checkByAge(User user, int age) throws AccessDeniedException, NullPointerException {
        if (user == null)
            throw new NullPointerException();

        if (user.getAge() >= age)
            return true;
        else
            throw new AccessDeniedException("User is too young");
    }

    public static List<User> getUserList() {
        List<User> allUser = new ArrayList<User>();
        allUser.add(new User("pavlik@gmail.com", "pavlik", "123", 37));
        allUser.add(new User("jack@gmail.com", "jack", "parol", 30));
        allUser.add(new User("antoshka@yandex.ru", "antosha", "behappy", 14));
        allUser.add(new User("zorka@gmail.com", "zarya", "wantcake", 26));

        return allUser;
    }
}
