package ru.netology;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AccessCheckHamcrestTest {
    // 1. Проверка, что метод getUserList выдает не NULL:
    @BeforeAll
    public static void getUserList_no_null() {
        System.out.println("getUserList_no_null");
        List<User> actual = AccessCheck.getUserList();
        assertThat(actual, notNullValue());
    }

    // 2. Проверка метода checkByAge на исключение NullPointerException:
    @Test
    public void checkByAge_NullPointerException() {
        System.out.println("checkByAge_NullPointerException");
        try {
            AccessCheck.checkByAge(null, 18);
        } catch (NullPointerException | AccessDeniedException e) {
            assertThat(e.getMessage(), nullValue());
        }
    }

    // 3. Проверка метода checkByAge на выброс исключения AccessDeniedException:
    @Test
    public void checkByAge_AccessDeniedException() {
        for (int age = 2; age < 99; age++) {
            System.out.println("checkByAge_AccessDeniedException  " + age);
            try {
                AccessCheck.checkByAge(new User("stepka@gmail.com", "stepka", "kotik", 1), age);
            } catch (NullPointerException | AccessDeniedException e) {
                assertThat(e.getClass(), equalTo(AccessDeniedException.class));
            }
        }
    }

    // 4. Проверка метода checkByAge на то, что исключение AccessDeniedException не будет выброшено:
    @Test
    public void checkByAge_not_AccessDeniedException() {
        boolean actual;
        for (Integer age = 0; age < 100; age++) {
            System.out.println("checkByAge_not_AccessDeniedException  " + age);
            actual = false;
            try {
                AccessCheck.checkByAge(new User("stepka@gmail.com", "stepka", "kotik", 100), age);
                actual = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            assertThat(true, equalTo(actual));
        }
    }

    // 5. Проверка, что метод checkByLoginPassword не выбрасывает исключения при вводе существующих
    // значений логина и пароля, и возвращает нужного пользователя:
    @Test
    public void checkByLoginPassword_rightUser() {
        System.out.println("checkByLoginPassword_rightUser");
        List<User> expectedUsers = AccessCheck.getUserList();
        List<User> actualUsers = new ArrayList<>();
        for (User user : expectedUsers) {
            try {
                actualUsers.add(AccessCheck.checkByLoginPassword(user.getLogin(), user.getPassword()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        assertThat(actualUsers, Matchers.containsInAnyOrder(expectedUsers.toArray()));
    }

    // 6. Проверка, что метод checkByLoginPassword выбрасывает исключение всегда,
    // когда пользователя с таким логином в базе нет, либо когда пароль не верен:
    public static Stream<User> getWrongUser() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "stepka", "123", 1));
        users.add(new User("2", "pavlik", "ponchik", 35));
        users.add(new User("3", "vania", "krokodil", 16));
        users.add(new User("4", "lisa", "alisa", 22));
        users.add(new User("5", "vovochka", "smile", 35));
        return users.stream();
    }

    @ParameterizedTest
    @MethodSource("getWrongUser")
    public void checkByLoginPassword_wrongUser(User user) {
        System.out.println("checkByLoginPassword_wrongUser");
        try {
            AccessCheck.checkByLoginPassword(user.getLogin(), user.getPassword());
            fail("Ожидаемое исключение не было выброшено");
        } catch (NullPointerException | UserNotFoundException e) {
            assertThat(e.getClass(), equalTo(UserNotFoundException.class));
        }
    }

    // 7. Проверка, что метод getUserList возвращает корректный список:
    @Test
    public void getUserList_rightList() {
        System.out.println("getUserList_rightList");
        List<User> actual = AccessCheck.getUserList();
        List<User> expected = new ArrayList<>();
        expected.add(new User("pavlik@gmail.com", "pavlik", "123", 37));
        expected.add(new User("jack@gmail.com", "jack", "parol", 30));
        expected.add(new User("antoshka@yandex.ru", "antosha", "behappy", 14));
        expected.add(new User("zorka@gmail.com", "zarya", "wantcake", 26));

        assertThat(actual, Matchers.containsInAnyOrder(expected.toArray()));
    }

    // 8. Проверка, что метод checkByLoginPassword выбрасывает исключение с верным сообщением:
    // 8.1. Логин верный, но пароль неправильный:
    @ParameterizedTest
    @ValueSource(strings = {"pavlik", "jack", "antosha", "zarya"})
    public void checkByLoginPassword_wrongUser_rightMessage_password(String login) {
        System.out.println("checkByLoginPassword_wrongUser_rightMessage_password");
        try {
            AccessCheck.checkByLoginPassword(login, "bla-bla");
            fail("Ожидаемое исключение не было выброшено");
        } catch (NullPointerException | UserNotFoundException e) {
            String s = e.getMessage();
            assertThat(e.getMessage(), is("Wrong password"));
        }
    }

    // 8.2. Логина нет в базе:
    @ParameterizedTest
    @ValueSource(strings = {"one", "two", "three", "four"})
    public void checkByLoginPassword_wrongUser_rightMessage(String login) {
        System.out.println("checkByLoginPassword_wrongUser_rightMessage");
        try {
            AccessCheck.checkByLoginPassword(login, "bla-bla");
            fail("Ожидаемое исключение не было выброшено");
        } catch (NullPointerException | UserNotFoundException e) {
            String s = e.getMessage();
            assertThat(e.getMessage(), is("User with login " + login + " is not registered"));
        }
    }
}
