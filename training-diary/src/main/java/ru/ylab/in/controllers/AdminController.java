package ru.ylab.in.controllers;

import ru.ylab.in.models.User;

import java.util.*;

public class AdminController {

    /**
     * Метод для вывода меню панели админа
     */

    public void printChooseMessage() {
        System.out.println(
                "\nВы находитесь в панели администратора. Здесь вам доступны такие функции, как: \n\n" +
                        "1. Вывести список пользователей\n" +
                        "2. Удалить пользователя\n" +
                        "3. Добавить новый тип тренировки\n" +
                        "4. Выйти из панели администратора\n\n" +

                        "Для выбора действия введите соответствующую цифру: \n");
    }

    /**
     * Метод для вывода списка юзеров из листа
    * @param userList лист, из которого выводится список пользователей
     */

    public void printUserList(List<User> userList) {

        System.out.println("Список пользователей: \n__________________________________________\n");

        for (User user : userList) {

            System.out.println("ID: " + user.getId() + "\n" +
                    "Логин: " + user.getLogin() + "\n" +
                    "Пароль: " + user.getPassword() + "\n" +
                    "Роль: " + user.getRole() + "\n\n"

            );
            System.out.println("__________________________________________\n");
        }

    }

    /**
     * Метод для удаления пользователя из листа
     * @param userList лист, из которого удаляется пользователь, логин которого мы введем
     * @param currentUser  текущий пользователь. Нужен для проверки, чтобы пользователь не мог удалить сам себя (для админа)
     */

    public void deleteUser(List<User> userList, User currentUser) {

        Scanner sc = new Scanner(System.in);
        int choose;

        boolean choosing = true;

        printUserList(userList);

        System.out.println("Введите ID юзера, которого хотите удалить:\n");

        while (choosing) {
            try {
                choose = sc.nextInt();
                if ((choose < 0 || choose > userList.size())) {
                    System.out.println("Пользователя с таким ID не существует!\n");
                } else {

                    if (currentUser.getId() != userList.get(choose).getId()) {
                        userList.remove(choose);
                        System.out.println("Пользователь удален!\n");
                        updateID(userList);
                        System.out.println("Вывожу обновленный список пользователей:\n");
                        printUserList(userList);
                        choosing = false;
                    } else {
                        System.out.println("Вы не можете удалить себя!\n");
                        choosing = false;
                    }
                }

            } catch (Exception e) {
                System.out.println("Введите число!\n");
                sc.nextLine();
            }


        }

        updateID(userList);

    }

    /**
     * Метод для обновления ID юзеров в листе
     * @param userList лист, в котором обновляются ID.
     *
     */

    public void updateID(List<User> userList) {

        Long id = 0L;
        if (userList.size() != 0) {
            for (int i = 0; i < userList.size(); i++) {
                userList.get(i).setId(id);
                id++;
            }
        }
    }

    /**
     * Метод возвращающий последнее значение ID в списке
     * @param userList лист, в котором проверяется какое значение ID яляется последним.
     *
     */

    public Long returnCurrentId(List<User> userList) {
        Long ID = (long) userList.size();

        return ID;
    }

    /**
     * Метод, добавляющий новую тренировку в лист тренировок.
     * @param workoutTypes Map, где Ключ соответствует упражнениям в листе (Кардио - бег, велосипед, бег в гору)
     * @param workoutParametres Map, где Ключ соответствует названиям параметров в листе (Кардио - расстояние, время, сожженные калории)
     *
     */

    public void addNewTraining(Map<String, List<String>> workoutTypes, Map<String, List<String>> workoutParametres) {

        int chooseOption = 0;


        Scanner sc = new Scanner(System.in);

        boolean choosingTraining = true;
        boolean choosingOption = true;

        boolean enteringName = true;
        boolean enterningParametres = true;

        boolean isExisting = false;

        String trainingName;
        String selectedWorkoutType;

        String param1 = null;
        String param2 = null;
        String param3 = null;

        String trainingParamName = "";

        System.out.println("Что вы хотите добавить?\n\n" +
                "1. Новый тип тренировок\n" +
                "2. Новое упражнение\n\n" +
                "Введите номер, соответствующий команде:\n");

        while (choosingOption) {

            try {

                chooseOption = sc.nextInt();

                if (chooseOption == 1 || chooseOption == 2) {
                    choosingOption = false;
               sc.nextLine();
                }

            } catch (Exception e) {
                System.out.println("Введите число!\n");
                sc.nextLine();
            }


            while (choosingTraining) {

                if (chooseOption == 1) {


                    System.out.println("Список существующих типов тренировок\n");

                    for (String workoutType : workoutTypes.keySet()) {
                        System.out.println(workoutType);
                    }


                    while (enteringName) {

                        isExisting = false;


                        System.out.println("Введите название нового типа:\n");
                        trainingParamName = sc.nextLine();

                        for (String workoutType : workoutTypes.keySet()) {
                            if (trainingParamName.equals(workoutType)) {
                                isExisting = true;
                                break;
                            }
                        }

                            if (!isExisting) {

                                enteringName = false;
                            } else {
                                System.out.println("Такой тип тренировок уже существует!\n");

                            }

                    }


                    System.out.println("Введите названия параметров упражнения (время, вес, подходы и т.п.):\n");
                    System.out.println("Параметр 1: \n");
                    param1 = sc.nextLine();
                    System.out.println("Параметр 2: \n");
                    param2 = sc.nextLine();
                    System.out.println("Параметр 3: \n");
                    param3 = sc.nextLine();

                    workoutParametres.put(trainingParamName, Arrays.asList(param1,param2,param3));
                    workoutTypes.put(trainingParamName, List.of());

                    System.out.println("Новый тип тренировок добавлен!");

                    choosingTraining = false;
                }


                if (chooseOption == 2) {

                    System.out.println("В какой тип тренировок вы хотите добавить новое упражнение?\n" +
                            "Введите название:\n");

                    for (String workoutType : workoutTypes.keySet()) {
                        System.out.println(workoutType);
                    }

                    selectedWorkoutType = sc.nextLine();

                    if (workoutTypes.containsKey(selectedWorkoutType)) {
                        System.out.println("Введите название упраженения: \n");

                        List<String> trainings = new ArrayList<>(workoutTypes.get(selectedWorkoutType));

                        trainingName = sc.nextLine();

                            trainings.add(trainingName);
                        workoutTypes.put(selectedWorkoutType, trainings);

                        choosingTraining = false;
                    }
                } else {
                    System.out.println("Введен неверный номер!\n");
                }
            }
        }
    }
}


