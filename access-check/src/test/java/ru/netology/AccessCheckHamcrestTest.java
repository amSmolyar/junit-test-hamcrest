package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
            fail("Ожидаемое исключение не было выброшено");
        } catch (NullPointerException | AccessDeniedException e) {
            assertThat(e.getCause(), instanceOf(NullPointerException.class));
        }
    }

    // 3. Проверка метода checkByAge на выброс исключения AccessDeniedException:
    public static Stream<Integer> getMaxIntValues() {
        Integer[] intArray = new Integer[99];
        for (Integer ii = 0; ii < intArray.length; ii++) {
            intArray[ii] = ii + 2;
        }
        return Arrays.stream(intArray);
    }

    @ParameterizedTest
    @MethodSource("getMaxIntValues")
    public void checkByAge_AccessDeniedException(int age) {
        System.out.println("checkByAge_AccessDeniedException  " + age);
        try {
            AccessCheck.checkByAge(new User("stepka@gmail.com", "stepka", "kotik", 1), age);
            fail("Ожидаемое исключение не было выброшено");
        } catch (NullPointerException | AccessDeniedException e) {
            assertThat(e.getCause(), instanceOf(AccessDeniedException.class));
        }
    }

    // 4. Проверка метода checkByAge на то, что исключение AccessDeniedException не будет выброшено:
    public static Stream<Integer> getMinIntValues() {
        Integer[] intArray = new Integer[99];
        for (Integer ii = 0; ii < intArray.length; ii++) {
            intArray[ii] = ii;
        }
        return Arrays.stream(intArray);
    }

    @ParameterizedTest
    @MethodSource("getMinIntValues")
    public void checkByAge_not_AccessDeniedException(int age) {
        System.out.println("checkByAge_not_AccessDeniedException  " + age);
        boolean actual = false;
        try {
            AccessCheck.checkByAge(new User("stepka@gmail.com", "stepka", "kotik", 100), age);
            actual = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertThat(true, equalTo(actual));
    }

    // 5. Проверка, что метод checkByLoginPassword не выбрасывает исключения при вводе существующих
    // значений логина и пароля, и возвращает нужного пользователя:
    public static Stream<User> getUser() {
        List<User> users = AccessCheck.getUserList();
        return users.stream();
    }

    @ParameterizedTest
    @MethodSource("getUser")
    public void checkByLoginPassword_rightUser(User user) {
        System.out.println("checkByLoginPassword_rightUser");
        User expectedUser = user;
        User getUser = new User();
        try {
            getUser = AccessCheck.checkByLoginPassword(user.getLogin(), user.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            getUser = null;
        } finally {
            User actualUser = getUser;
            assertAll("checkByLoginPassword_rightUser",
                    () -> assertDoesNotThrow(() ->
                            AccessCheck.checkByLoginPassword(user.getLogin(), user.getPassword())),
                    () -> assertEquals(expectedUser, actualUser));
        }
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

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                AccessCheck.checkByLoginPassword(user.getLogin(), user.getPassword())
        );

        String message = e.getMessage();
        assertTrue(message.equals("Пароль не верный") || message.equals("Пользователь с таким логином не зарегистрирован"));
    }
}
