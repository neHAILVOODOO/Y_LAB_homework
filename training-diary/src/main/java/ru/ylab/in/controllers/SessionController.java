package ru.ylab.in.controllers;

import ru.ylab.in.models.User;

import java.util.List;
import java.util.Scanner;

public class SessionController {

    /**
     * Метод для добавления пользователя в юзерлист (регистрация)
     * @param id ID пользователя (нужен для админа)
     * @param userList лист, в который добавляется пользователь
     */
    public User register (Long id, List<User> userList) {

        String login;
        String password;
        String password2;
        String role = "USER";

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nВы находитесь в панели регистрации. Следуйте следующим инструкциям, \n" +
                "чтобы зарегистрироваться: \n");

        System.out.println("Введите ваш логин:");
        login = scanner.nextLine();

        System.out.println("Введите ваш пароль:");
        password = scanner.nextLine();

        System.out.println("Подтвердите пароль:");
        password2 = scanner.nextLine();

        while (!password2.equals(password)) {
            System.out.println("Подтвердите пароль:");
            password2 = scanner.nextLine();
        }

        for (User existingUser : userList) {
            if (existingUser.getLogin().equals(login)) {
                return null;
            }
        }

        return new User(id, login, password, role);

    }

    /**
     * Метод для проверки, соответствуют ли введенные данные данным пользователя из списка.
     * Если да, то возвращается юзер, с которым совпали данные.
     *
     * @param userList лист пользователя, в котором делается проверка
     */

    public User login(List<User> userList) {

        String login;
        String password;

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nВы находитесь в панели логина. Следуйте следующим инструкциям, \n" +
                "чтобы войти в аккаунт: \n");

        System.out.println("Введите ваш логин:");
        login = scanner.nextLine();

        System.out.println("Введите ваш пароль:");
        password = scanner.nextLine();


        for (User existingUser : userList) {
            if (existingUser.getLogin().equals(login) && existingUser.getPassword().equals(password)) {
                return existingUser;
            }
        }
        return null;
    }

    /**
     * Метод для добавления администратора.
     * Добавить можно только одного.
     *
     * @param id ID администратора
     * @param userList лист, в который добавляется администратор
     */

    public User addAdmin(Long id, List<User> userList) {

        for (User existingUser : userList) {
            if (existingUser.getLogin().equals("admin")) {
                System.out.println("Вы уже добавили админа!\n");
                return null;
            }
        }

        System.out.println("Был добавлен администратор!\n");
        return new User(id, "admin", "root", "ADMIN");
    }

    /**
     * Метод для вывода сообщения с информацией о разработчике, возможностях проекта и секретными командами
     */

    public void printHelpMessage() {
        System.out.println("\nПроект разработан в рамках интенсива Y_LAB.\n" +
                "Название проекта: <Training Diary>\n" +
                "Автор проекта: Кухтин И.В.\n" +
                "Гитхаб автора: https://github.com/neHAILVOODOO/Y_LAB_homework\n\n" +
                "Немного о функциях:\n\n" +
                "Для юзера:\n" +
                "1. Регистрация и вход в аккаунт пользователя (создание пользователей с одинаковыми логинами невозможно)\n" +
                "2. Соответственно, в системе может быть несколько аккаунтов и между ними можно переключаться\n" +
                "3. Пользователь может добавлять записи тренировок в свой профиль. Список тренировок выводится, когда выбираешь добавить новую запись.\n" +
                "4. Пользователь может редактировать записи тренировок\n" +
                "5. Пользователь может удалять записи тренировок.\n" +
                "6. Пользователь может вывести статистику по шагам, количеству сожженных калорий и количеству тренировок.\n" +
                "7. Возможность выйти из аккаунта (система запоминает все введенные данные)\n\n" +
                "Для админа:\n" +
                "1. Все функции юзера, соответственно, доступны и админу.\n" +
                "2. Админ может вывести список пользователей, где будет доступна информация: ID, логин, пароль и роль пользователей.\n" +
                "3. Админ может удалить пользователя по его ID\n" +
                "4. Админ НЕ МОЖЕТ удалить себя!!!\n" +
                "5. Админ может добавить в список тренировок новую тренировку.\n\n" +
                "_________________________________________________________\n" +
                "\nДЛЯ ТОГО, ЧТОБЫ СОЗДАТЬ АДМИНА, В ГЛАВНОМ МЕНЮ ВВЕДИТЕ В КОНСОЛЬ <101>." +
                "ЛОГИН И ПАРОЛЬ АДМИНА БУДУТ: \n Логин: <admin> \n Пароль: <root>\n" +
                "\n_________________________________________________________\n\n");
    }


}
